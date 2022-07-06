/**
 * 
 */
package hashimotonet.service;

import static hashimotonet.util.InputHandler.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import hashimotonet.bean.URLHolder;
import hashimotonet.model.Photo;
import hashimotonet.util.BaseUtil;
import hashimotonet.util.FileProcessorUtil;

/**
 * @author hashi
 *
 */
@Service
public class ListImagesService {
	
	/**
	 * 画像データアクセスサービス
	 */
	private final PhotoService service;
	
	@Autowired
	public ListImagesService(PhotoService service) {
		this.service = service;
	}

	/**
	 * Logger.
	 */
    private Logger log = LogManager.getLogger(ListImagesService.class);

    /**
     * 一覧表示画面処理における主処理を行います。
     *
     * @param request HTTP要求電文
     * @param response HTTP応答電文
     * @return 処理結果
     * @throws ClassNotFoundException クラスが見つからない例外
     * @throws SQLException SQL例外
     * @throws IOException 入出力例外
     * @throws URISyntaxException URIシンタックス例外
     * @throws ServletException サーブレット例外
     */
    public String execute(HttpServletRequest request, String id)
            throws ClassNotFoundException,
                    SQLException,
                    IOException,
                    URISyntaxException,
                    ServletException {
        // 返却値をFALSEで初期化
        boolean result = false;
        
        /*
         *  Android端末からの要求であるかについて、ユーザーエージェントにて判定。
         */
        final String USER_AGENT = "User-Agent";
        final String LINUX = "Linux;";
        final String ANDROID = "Android";
        String userAgent = request.getHeader(USER_AGENT);
        int linux = userAgent.indexOf(LINUX);
        int android = userAgent.indexOf(ANDROID);
        boolean smartPhone = false;
        
        // クライアント端末判定
        if(linux > 0 && android > 0) {
        	smartPhone = true;
        }
        
        // 入力ストリーム取得。
        InputStream is = request.getInputStream();

        String req = is2String(is);

        String name = "&status=";
        //String id = null;
        String status =  null;
        
        log.debug("req = : " + req);

        /*
        if (req.contains(name)) {
            id = req.substring("id=".length(), req.indexOf(name));
            status = req.substring(req.indexOf(name) + name.length());
        } else {
            //id = request.getParameter("id");
        }*/

        log.debug("req = " + req);

        id = request.getParameter("id");
        
        if (id == null) {
        	id = (String) request.getAttribute("id");
        }
        
        if (id == null) {
        	id = req.split("=")[1];
        }
        
        // 「@」はURLエンコードされるので置換。
        if (id != null) {
        	id = id.replace("%40", "@");
        }

        // ログ出力する
        log.debug("id =" + id + " : status = " + status);

        // アップロードされた画像があるか判定する。
        boolean uploaded = false;
        if (BaseUtil.isNotEmpty(status)) {
            if (status.equals("uploaded")) {
                uploaded = true;
            }
        }

        // id で示されるディレクトリが存在するか？
        File parent = FileProcessorUtil.getParentDir(id, request);

        log.debug("parent = " + parent.getName());

        boolean exists = parent.exists();

        log.debug("exists = " + exists);

        // 一覧画像ファイルのファイル名群
        List<URLHolder> files = null;
//        if ((exists) && (uploaded == false)) {
        if (exists) {

            // 親ディレクトリが既に存在する場合。
            log.info("親ディレクトリが存在するので取得のみ。");

            // ディレクトリ内を走査し、存在するファイルを取得する。
            files = FileProcessorUtil.getListFiles(parent);

        }else {
            // 親ディレクトリが存在しない場合。
            log.info("親ディレクトリが存在しない。");

            // DAOクラスのインスタンスを作成
            //PhotoDao dao = new PhotoDao();

            // IDが同一であるもののレコードの画像データを取得する
            //List<Photo> images = dao.selectPhotoBlobsById(id);
            List<Photo> images = service.getAllPhotoList(id);
            
            // DAOのクローズ
            //dao.close();

            // 要求電文、ID、画像データを引数に、画像の一時イメージファイル集を
            // 作成し、ファイル一覧を戻り値として取得する。
            files = FileProcessorUtil.writeImageById(request.getServletContext(),
                                                      id,
                                                      images);

        }

        // 画像ファイルURL群を生成。
        List<URLHolder> urls = createUrlPaths(files, request);

        // クライアントへ要求処理結果に関して応答を行う。
        request.setAttribute("id", id);

        String output = null;//new JsonNode().encode(urls, true);
        ObjectMapper mapper = new ObjectMapper();
        output = mapper.writeValueAsString(urls);
        
        log.trace(output);
        
        return output;
        
        /**

        if (smartPhone) {
            out.println(output);
        } else {
        	String path = "/WEB-INF/display.jsp";
        	
            //request.setCharacterEncoding("UTF-8");
        	response.setContentType("text/json; charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            request.setAttribute("images", output);
            request.getServletContext().getRequestDispatcher(path).forward(request, response);
        }

        
        // 処理は成功したので、処理結果を真とする。
        result = true;

        // 処理結果を返却する。
        return result;
        
        */
    }


    /**
     * 画像ファイルのURL表現群を作成し返却します。
     *
     * @param files 元となるURLファイルパス
     * @param req HTTP要求電文
     * @return JSON応答値となるリストオブジェクト
     * @throws MalformedURLException 不正なURL例外
     */
    private  List<URLHolder> createUrlPaths(List<URLHolder> files, HttpServletRequest req)
            throws MalformedURLException {

        List<URLHolder> result = new ArrayList<URLHolder>();
        URLHolder bean = new URLHolder();

      ServletContext sc = req.getServletContext();
      
      // 送信元URLを取得
      String requestUrl = req.getRequestURL().toString();
      boolean https = false;
      if (requestUrl != null) {
    	  if (requestUrl.startsWith("https://")) {
    		  https = true;
    	  }
      }
      
      for(URLHolder file : files) {
          // 画像イメージのURLを生成する。
          String url = null;
          if (https) {
        	  url = "https://";
          } else {
        	  url = "http://";
          }
          url = url 
              + req.getServerName()
              + ":" + req.getServerPort()
              + sc.getContextPath()
              + file.getUrl();
          log.debug("url    = " + url);

          // URLHolderにURLをセット。
          bean.setUrl(url);

          // サムネイルイメージのURLを生成する。
          String thumb = "http://"
                  + req.getServerName()
                  + ":" + req.getServerPort()
                  + sc.getContextPath()
                  + file.getThumbnail();
          log.debug("thumb = " + thumb);

          // URLHolderにサムネイルURLをセット。
          bean.setThumbnail(thumb);
          
          // altテキストをセット。
          bean.setAlt(file.getAlt());
          log.debug("alt = " + file.getAlt());

          // 返却値であるリストに、値のセットされたURLHolderを追加。
          result.add(bean);

          // 値を追加した変数を新規インスタンスとして初期化。
          bean = new URLHolder();
      }

      // JSON応答値となるリストを返却する。
      return result;
    }

}
