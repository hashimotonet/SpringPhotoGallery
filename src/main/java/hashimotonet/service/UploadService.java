/**
 * 
 */
package hashimotonet.service;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hashimotonet.bean.RequestBean;
import hashimotonet.model.Photo;
import hashimotonet.util.FileProcessorUtil;

/**
 * @author hashi
 *
 */
@Service
@Transactional
public class UploadService {

	private final PhotoService service;
	
	@Autowired
	public UploadService(PhotoService service) {
		this.service = service;
	}
	
	/**
	 * Logger.
	 */
    private Logger log = LogManager.getLogger(UploadService.class);

    /**
	 * 
	 */
	public UploadService() {
		super();
		this.service = new PhotoService();
	}

    public boolean execute (HttpServletRequest request, RequestBean reqBean)
            throws Exception {

        // 戻り値をFALSEで初期化
        boolean result = false;

        // モデルクラスの生成
        Photo photo = new Photo();

        // Base64クラスの宣言をする
        String base64 = reqBean.getData();
        //System.out.println("base64=" + base64);
        //log.warn("base64=" + base64);

        // IDを取得する。
        // '@'文字はURLエンコードされているので、
        // 変換を行う。
        String identity = reqBean.getIdentity();
        identity = identity.replace("%40", "@");

        log.info("identity = " + identity);

        // Base64文字列をバイト配列に複合する。後にBLOBのDB格納値となる。
        byte[] data = decode2Bytes(base64);

        // Base64文字列が空文字でなければアップロード完了
        if ((!base64.equals("")))
            log.info("アップロード完了！");
        else
            log.warn("データがアップロードされていません！");

        // モデルクラスに値をセットする。
        photo.setIdentity(identity);
        photo.setAuthority(1);    // TODO クライアントの権限レベル設定は未実装
        photo.setData(data);
        photo.setAlt(reqBean.getAlt());

        try {

            log.info("try節に入る！");

            // Photo表へインサート実行
            service.create(photo);

            // ログ出力する
            log.info("インサート完了！");

            // ファイルを書き込む。
            String name = FileProcessorUtil.writeOneImageById(request.getServletContext(),identity,photo);

            // ログ出力する。
            log.info("ファイル書き込み完了！：" + name);

        } catch(Exception e) { // その他の例外発生時

            // ログ出力する
            log.catching(e);

            // ログ出力する
            log.info("例外発生！ロールバックします。");

            // 例外をスローします。
            throw e;
        }

        // 処理結果を真とする。
        result = true;

        // 処理結果を返却する。
        return result;
    }

    /**
     * 引数のクエリ文字列より、パラメータを抽出します。
     *
     * @param queryString 要求電文文字列
     * @return 要求電文のString配列化表現
     */
    private String[] getRequestedParameters(String queryString) {
        String result[] = new String[2];
        int begin = "id=".length();
        String delimiter = "&base64=";
        int end = queryString.indexOf("&base64=");
        int next = delimiter.length();
        String first = queryString.substring(begin, end);
        result[0] = first;
        String second = queryString.substring(end + next);
        result[1] = second;

        return result;
    }

    private String getBase64String(String src) {
        String delimiter = ",";
        if (src == null) {
        	return null;
        }
        String result = src.substring(src.indexOf(delimiter) + 1, src.lastIndexOf("\""));

        return result;
    }

    private static byte[] decode2Bytes(String encoded) {
    	String formatted = "";
    	if (encoded != null) {
    		if (encoded.indexOf(",") > 0) {
    			int start = encoded.indexOf(",") + ",".length();
    			encoded = encoded.substring(start);
    		}
    	}
        if (Base64.isBase64(encoded)) {
            return Base64.decodeBase64(encoded);
        } else {
        	throw new RuntimeException("Submitted Data String is not a valid Base64 String:\r\n" + encoded);
        }
    }
}
