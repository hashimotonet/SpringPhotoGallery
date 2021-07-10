package hashimotonet.util;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hashimotonet.bean.URLHolder;
import hashimotonet.model.Photo;
import hashimotonet.service.PhotoService;
import hashimotonet.util.image.SquareFileCreator;
import hashimotonet.util.image.ThumbnailCreator;

@Component
public final class FileProcessorUtil {

    private static final Logger log = LogManager.getLogger(FileProcessorUtil.class);
    
    private static PhotoService service;
    
    @Autowired
    public FileProcessorUtil(PhotoService service) {
    	FileProcessorUtil.service = service;
    }

    public static final String SEP = System.getProperty("file.separator");

    /**
     * 第一引数のパスに第二引数のバイト配列であるバイナリデータを
     * 書き込み、バイナリファイルを生成します。
     *
     * @param path 書き込み先のファイルパス
     * @param bytes 書き込み対象であるバイナリデータ
     * @throws IOException 入出力例外
     */
    public static File bytes2File(String path, Photo bytes)
            throws IOException {

        // ファイルオブジェクトを生成
        File file = new File(path);
        
        // ファイルはコンテナ終了時にオミットする。
        file.deleteOnExit();

        // ファイルオブジェクトより、ファイル出力ストリーム、バッファ出力ストリームを
        // 生成する。
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));

        // バッファ出力ストリームに書き込み。
        bos.write(bytes.getData());

        // バッファ出力ストリームをフラッシュ。
        bos.flush();

        // バッファ出力ストリームをクローズ。
        bos.close();

        return file;



    }

    public static void byte2Jpeg(String path, byte[] bytes)
            throws IOException {
        byte[] imageBinary = bytes;
        BufferedImage bufImage = aaa(imageBinary);
        OutputStream out = new FileOutputStream(path);
        ImageIO.write(bufImage, "jpg", out);
        bufImage.flush();
    }

    private byte[] decodeBase64Bytes(byte[] src) {
        byte[] result = null;

        result = Base64.decodeBase64(src);

        return result;

    }

    public static List<URLHolder> getListFiles(File parent) throws ClassNotFoundException, SQLException, IOException, URISyntaxException {
        List<String> result = new ArrayList<String>();
        List<URLHolder> holder = new ArrayList<URLHolder>();    // 画像とサムネイル画像のURLを保持する
        URLHolder bean = new URLHolder();

        File[] list = parent.listFiles();
        for (File file : list) {
            String path = file.getName();
            log.debug("path = " + path);
            if(path.equals("thumbnail") == false) {
                result.add("/" + parent.getName() + "/" + path);
            }
        }

        result = BaseUtil.sort(result);
        
        //List<Photo> altList = new PhotoDao().getAltByIdIdentity(parent.getName());
        log.debug("parent.getName() = " + parent.getName());
        List<Photo> altList = service.getAllPhotoList(parent.getName());

        for (String url : result) {
            bean.setUrl(url);
            bean.setThumbnail("/" + parent.getName() + "/thumbnail/" + url.substring(url.lastIndexOf("/") + 1));
        	for (Photo photo : altList) {
        		String urlComp = url.substring(url.lastIndexOf("/") + "/".length(), url.lastIndexOf("."));
				String idComp = String.valueOf(photo.getId()); 
        		if (urlComp.equals(idComp)) {
        			bean.setAlt(photo.getAlt());
        			break;
        		}
        	}
            holder.add(bean);
            bean = new URLHolder();
        }

        return holder;
    }
    
    /**
     * フォルダ名がID名であるディレクトリを作り、
     * IDに紐付いたイメージをファイルとして書き込みます。
     *
     * @param request 要求電文
     * @param id ID
     * @param images 画像バイナリ
     * @return ファイル名称群のリスト
     * @throws FileNotFoundException ファイルが見つからない例外
     * @throws IOException 入出力例外
     * @throws URISyntaxException
     * @throws NumberFormatException
     */
    public static synchronized List<URLHolder> writeImageById(ServletContext context,
            String identity,
            List<Photo> images)
            throws FileNotFoundException, IOException, NumberFormatException, URISyntaxException {

        // 作成するファイル群のリストを初期化
        List<URLHolder> fileList = new ArrayList<URLHolder>();
        URLHolder holder = new URLHolder();

        // ID名であるディレクトリを作成
        String directory = mkdir(identity, context);

        // カウンタ初期化
        // int index = 1;

        // イテレーター取得
        Iterator<Photo> iterator = images.iterator();
        Photo photo = null;

        // イテレーターのループ
        while (iterator.hasNext()) {
        	photo = iterator.next();
        	
        	String id = String.valueOf(photo.getId());

            // フォルダのパスとカウンタをファイル名でパスを生成。
            String path = directory + SEP + id + ".jpg";

            log.info("path = " + path);

            // パスにバイトバイナリデータでのファイルを生成。
            File original = bytes2File(path, photo);

            // サムネイル画像を生成する。
            String dir = directory + SEP + "thumbnail" + SEP;
            String inputFile = original.getAbsolutePath();
            String outputFile = id + ".jpg";
            double rate = Double.valueOf(new PropertyUtil("photograph.properties").get("rate"));

            File thumb = new File(dir);
            boolean made = true;

            if (thumb.exists() == false) { // ない。
                made = thumb.mkdirs();       // ないので作る。
            }

            if (made == false) { // 無くて、作れなかなった。
                throw new FileNotFoundException("サムネイルディレクトリの作成に失敗しました。：\n" + dir);
            }

            ThumbnailCreator thumbCreator
                = new ThumbnailCreator(dir,
                                       inputFile,
                                       outputFile,
                                       rate);
            //thumbCreator.createThumbnail();

            // ファイルを正方形に加工する。
            SquareFileCreator squeare
                = new SquareFileCreator(outputFile, outputFile);

            // ファイルURLを作成
            String url = "/" + identity + "/" + id + ".jpg";

            // サムネイル画像ファイルURLを作成
            String thumbUrl = "/" + identity + "/" + "thumbnail/" + id + ".jpg";
            //String thumbUrl = null;

            // ファイルパスをファイル群のリストに追加
            holder.setUrl(url);
            holder.setThumbnail(thumbUrl);

            fileList.add(holder);

            holder = new URLHolder();

        }

        // ファイルパス群のリストを返却
        return fileList;
    }

    public static String writeOneImageById(ServletContext context,
            String identity,
            Photo image)
            throws IOException, URISyntaxException {
    	
    	// ファイル名取得
    	String fileName = String.valueOf(image.getId());

        // 作成するファイル群のリストを初期化
        List<String> fileList = new ArrayList<String>();

        // ID名であるディレクトリを作成
        String directory = mkdir(identity, context);

        // ディレクトリファイルを取得
        File parent = new File(directory);
        File[] files = parent.listFiles();
        for (File file : files) {
            log.debug("file.getName() = " + file.getName());
            fileList.add(file.getName());
        }

        // リストより"thumbnail"を削除。
        fileList.remove("thumbnail");

        // ファイル名でソートする。
        BaseUtil.sort(fileList);

        // "thumnail" であるインデックスを削除する。


        // 最大のインデックスを持つファイル名を取得する。
        String maxFile = null;
        String index = null;
        if (fileList.size() > 0) {
            maxFile = fileList.get(fileList.size() - 1);
            index = maxFile.split(".jpg")[0];
        } else {
            index = "0";
        }
        int number = 0;

        // 取得インデックスが数値であることの判定。
        Pattern p = Pattern.compile("^[0-9]+$");
        Matcher m = p.matcher(index);
        if (m.find() == true) {
            // 正であればインクリメントする。
            number = Integer.valueOf(index).intValue() + 1;
        } else {
            number++;
        }

        // フォルダのパスとカウンタをファイル名でパスを生成。
        String path = directory + SEP + fileName + ".jpg";
        String thumbPath = directory + SEP + "thumbnail"  + SEP + fileName + ".jpg";
        String thumbDir = SEP + "thumbnail" + SEP + fileName + ".jpg";

        log.info("path = " + path);

        // パスにバイトバイナリデータでのファイルを生成。
        File newOne = bytes2File(path, image);

        // サムネイル画像作成
        double rate = Double.valueOf(new PropertyUtil("photograph.properties").get("rate")).doubleValue();
        ThumbnailCreator thumb = new ThumbnailCreator(directory,
                                                       newOne.getAbsolutePath(),
                                                       thumbDir,
                                                     rate);
        // TODO 暫定コメントアウト。
        //thumb.createThumbnail();

        // 結果を返却。
        return path;
    }

    /**
     * ディレクトリ作成メソッド
     *
     * @param id IDであり、ディレクトリ名として使用する
     * @param request HTTP要求
     * @return 作成ディレクトリのパス
     * @throws IOException 入出力例外
     */
    public static String mkdir(String id,
            ServletContext context)
            throws IOException {

        // 当Webアプリケーションルートの、絶対パスを取得
        String parent = context.getRealPath("/");

        // 親ディレクトリにIDの子ディレクトリ名を付加
        String path = parent + id;

        // ログ出力する。
        log.info(path);

        // パスよりファイルオブジェクト作成
        File dir = new File(path);

        // ディレクトリは存在しないこと
        if (dir.exists()) {
            // nop.
        } else {
            // 作成対象はディレクトリでないこと
            if (dir.isDirectory()) {
                // nop.
            } else {
                // ディレクトリは存在しないので、新規作成する。
                dir.mkdir();
            }
        }

        // 作成したディレクトリパスを返却
        return path;
    }

    /**
     * 当Webアプリケーションルートの、絶対パスを取得する。
     *
     * @param id
     * @param request
     * @return
     */
    public static File getParentDir(String id, HttpServletRequest request) {
        // 当Webアプリケーションルートの、絶対パスを取得
        String parent = request.getServletContext().getRealPath("/");

        // 親ディレクトリにIDの子ディレクトリ名を付加
        String path = parent + id;

        log.debug("path = " + path);

        return new File(path);

    }

    public static File getParentDir(String id, ServletContext context) {
        // 当Webアプリケーションルートの、絶対パスを取得
        String parent = context.getRealPath("/");

        // 親ディレクトリにIDの子ディレクトリ名を付加
        String path = parent + id;

        log.debug("path = " + path);

        return new File(path);

    }

    public static BufferedImage aaa(byte[] imageBinary) throws IOException {
        BufferedImage img = ImageIO.read(new ByteArrayInputStream(imageBinary));

        int width = img.getWidth();
        int height = img.getHeight();
        BufferedImage bufImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int c = img.getRGB(x, y);
                int r = r(c);
                int g = g(c);
                int b = b(c);
                int rgb = rgb(r, g, b);
                bufImage.setRGB(x, y, rgb);
            }
        }

        return bufImage;
    }

    //以下、シフト演算ビット演算のメソッド
    public static int a(int c) {
        return c >>> 24;
    }

    public static int r(int c) {
        return c >> 16 & 0xff;
    }

    public static int g(int c) {
        return c >> 8 & 0xff;
    }

    public static int b(int c) {
        return c & 0xff;
    }

    public static int rgb(int r, int g, int b) {
        return 0xff000000 | r << 16 | g << 8 | b;
    }

    public static int argb(int a, int r, int g, int b) {
        return a << 24 | r << 16 | g << 8 | b;
    }

}
