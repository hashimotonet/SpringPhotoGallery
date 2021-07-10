/**
 *
 */
package hashimotonet.util.image;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Osamu Hashimoto
 *
 */
public class ThumbnailCreator {

    /**
     * 画像ディレクトリパス
     */
    private String directory;

    /**
     * 入力ファイル名
     */
    private String inputFile;

    /**
     * 出力ファイル名
     */
    private String outputFile;

    /**
     * 画像縮小倍率
     */
    private double rate;

    /**
     * ロガー
     */
    private Logger log = LogManager.getLogger(ThumbnailCreator.class);

    /**
     * デフォルトコンストラクタ。
     * 使用禁止。
     */
    protected ThumbnailCreator() {
        throw new UnsupportedOperationException();
    }

    /**
     * コンストラクタ。
     * 通常はこちらを使用すること。
     *
     * @param directory 作成先のディレクトリ
     * @param inputFile 元画像ファイル
     * @param outputFile サムネイル画像ファイル
     * @param rate 収縮倍率
     */
    public ThumbnailCreator(String directory,
                             String inputFile,
                             String outputFile,
                             double rate) {
        this.directory = directory;
        this.inputFile = inputFile;
        this.outputFile = outputFile;
        this.rate = rate;
    }

    /**
     * サムネイル画像を作成します。
     *
     * @param inputFile 変換元の画像ファイル名
     * @param outputFile 変換後の画像ファイル名
     * @param rate 画像の倍率
     * @throws IOException 入出力エラーが発生した場合
    */
    public void createThumbnail()
            throws IOException {
        FileOutputStream out = null;
        int startX = 0; // 画像の切り出しX座標
        int startY = 0; // 画像の切り出しY座標

        try {
            int index;
            String extension = "";

            // 入力ファイルの拡張子チェック
            index = inputFile.lastIndexOf(".");
            if (index != -1) {
                extension = inputFile.substring(index + 1);
            }
            if (!isReaderFormat(extension)) {
                // 読み込みがサポートされていない場合
                throw new IOException("Not Supported Reader Format!");
            }
            BufferedImage image_1;

            image_1 = ImageIO.read(new File(this.inputFile));
            if (image_1 == null) {
                throw new IOException("Not Image File!");
            }

            // 元の画像の幅
            int width_1 = image_1.getWidth();
            log.debug("元の画像の幅=" + width_1);

            // 元の画像の高さ
            int height_1 = image_1.getHeight();
            log.debug("元の画像の高さ=" + height_1);

            // イメージ型
            int type = image_1.getType();
            if (type == BufferedImage.TYPE_CUSTOM) {
                // イメージ型が不明な場合
                type = BufferedImage.TYPE_INT_RGB;
            }

            // サムネイル画像の幅
            int width_2 = (int) (image_1.getWidth() * rate);
            log.debug("サムネイル画像の幅=" + width_2);

            // サムネイル画像の高さ
            int height_2 = (int) (image_1.getHeight() * rate);
            log.debug("サムネイル画像の高さ=" + height_2);

            // サムネイル画像作成
            BufferedImage image_2;
            image_2 = new BufferedImage(width_2, height_2, type);


            // 画像を正方形に切り出し。
            if (height_2 >= width_2) {
                startX = 0;
                startY = (height_2 - width_2) / 2;
                log.info("startX=" + startX + " : startY=" + startY + " : width_2=" + width_2);
                image_2 = image_2.getSubimage(startX, startY, width_2, width_2);
            } else {
                startX = (width_2 - height_2) / 2;
                startY = 0;
                log.info("startX=" + startX + " : startY=" + startY + " : height_2=" + height_2);
                image_2 = image_2.getSubimage(startX, startY, height_2, height_2);
            }

            AffineTransform at;
            at = AffineTransform.getScaleInstance(rate, rate);
            AffineTransformOp ato;
            ato = new AffineTransformOp(at, null);
            ato.filter(image_1, image_2);

            // 出力ファイルの拡張子チェック
            index = outputFile.lastIndexOf(".");
            if (index != -1) {
                extension = outputFile.substring(index + 1);
            }
            if (!isWriterFormat(extension)) {
                // 書き出しがサポートされていない場合
                extension = "jpg";
            }

            // サムネイル画像保存
            File outFile = new File(this.directory , this.outputFile);
            out = new FileOutputStream(outFile);
            ImageIO.write(image_2, extension, out);

            // BufferedImageをflushする。
            image_2.flush();
            image_1.flush();

            // 画像を90℃回転する。
            outFile = rotateImage(outFile.getAbsolutePath());
        } catch(Exception e) {
            log.catching(e);

        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    /**
     * イメージの回転処理を行います。
     *
     * @param filePath 処理対象ファイル
     * @return 右へ90℃回転したファイル。
     */
    private File rotateImage(String filePath) {
        try {
            BufferedImage inBuff = ImageIO.read(new File(filePath));
            AffineTransform affine = new AffineTransform();
            //90度のときの回転処理
            affine.setToRotation(Math.toRadians(90), inBuff.getHeight()/2, inBuff.getHeight()/2);
            //180度のときの回転処理
            //affine.setToRotation(Math.toRadians(180), inBuff.getWidth()/2, inBuff.getHeight()/2);
            //270度のときの回転処理
            //affine.rotate(Math.toRadians(270), inBuff.getWidth()/2, inBuff.getWidth()/2);

            AffineTransformOp op = new AffineTransformOp(affine, AffineTransformOp.TYPE_BICUBIC);
            //180度のときのアウトプット画像バッファ
            //BufferedImage outBuff = new BufferedImage(inBuff.getWidth(), inBuff.getHeight(), inBuff.getType());
            //90度、270度のときのアウトプット画像バッファ
            BufferedImage outBuff = new BufferedImage(inBuff.getHeight(), inBuff.getWidth(), (inBuff.getType()==0?5:inBuff.getType()));
            op.filter(inBuff, outBuff);

            // ファイルへ書き込み。
            File file = new File(filePath);
            FileOutputStream fos = new FileOutputStream(file);
            ImageIO.write(outBuff, "jpg", fos);

            return file;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
    * 読み込みがサポートされている画像フォーマットかどうか
    * チェックします。
    *
    * @param extension 画像ファイルの拡張子
    * @return 読み込みがサポートされている場合はtrue、そうでない場合はfalse
    */
    private static boolean isReaderFormat(String extension) {
        String[] reader = ImageIO.getReaderFormatNames();
        for (int i = 0; i < reader.length; i++) {
            if (reader[i].equalsIgnoreCase(extension)) {
                return true;
            }
        }
        return false;
    }

    /** * 書き出しがサポートされている画像フォーマットかどうか * チェックします。
    *
    * @param extension 画像ファイルの拡張子
    * @return 書き出しがサポートされている場合はtrue、そうでない場合はfalse
    */
    private static boolean isWriterFormat(String extension) {
        String[] writer = ImageIO.getWriterFormatNames();
        for (int i = 0; i < writer.length; i++) {
            if (writer[i].equalsIgnoreCase(extension)) {
                return true;
            }
        }
        return false;
    }
}
