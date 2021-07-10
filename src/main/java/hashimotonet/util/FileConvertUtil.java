package hashimotonet.util;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public final class FileConvertUtil {

    /**
     * 当クラスのロガー
     */
    private static final Logger log = LogManager.getLogger(FileConvertUtil.class);

    /**
     * プライベートコンストラクタ
     */
    private FileConvertUtil() {

    }

    /**
     * Base64エンコードメソッド
     *
     * @param base64 Base64文字列
     * @return エンコードされたバイト配列であるバイナリデータ。
     */
    public static byte[] encodeBase64String2ByteArray(String base64)
            {
        byte[] data = null;
        data = Base64.encodeBase64(base64.getBytes());
        return data;
    }

    /**
     * Base64デコードメソッド
     *
     * @param encoded エンコードされたBase64文字列
     * @return デコードされたバイト配列であるバイナリデータ
     */
    public static byte[] base64String2ByteArray(String encoded) {
        byte[] decoded = Base64.decodeBase64(encoded);
        return decoded;
    }

    /**
     * Base64 デコードメソッド
     * 主に要求電文から取得した入力ストリームからの
     * バイト配列を複合（デコード）されたバイナリ
     * データとして返却します。
     *
     * @param param 入力ストリームより取得したBase64エンコードされた
     * バイト配列
     *
     * @return 複合されたバイト配列であるバイナリデータ
     */
    public static byte[] base64ByteArray2BinaryFromInputStream(byte[] requestData)
             throws UnsupportedEncodingException {
        byte[] data = null;

        data = Base64.decodeBase64(requestData);

        return data;
    }

    /**
     * Webアプリケーション版・デコードメソッド
     *
     * @param encoded エンコードされたBase64文字列
     * @return デコードされたバイト配列であるバイナリデータ
     */
    public static byte[] normalBase64Decode(String encoded) {

        byte[] result = Base64.decodeBase64(encoded);

        return result;

    }


    /*
     * 現在未使用
     */
    @SuppressWarnings("unused")
    private static String getNotNameOnlyValue(byte[] requestData)
            throws UnsupportedEncodingException {
        String base64 = null;

        base64 = new String(requestData);
        base64 = base64.substring("data=".length());

        //log.info(base64);

        return base64;
    }
}
