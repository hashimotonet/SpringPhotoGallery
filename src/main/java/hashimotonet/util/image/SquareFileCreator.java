package hashimotonet.util.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 *
 * @author Osamu Hashimoto
 *
 */
public class SquareFileCreator {

    // 起点座標値
    private int x, y;

    // 画像サイズ
    private int width, height;

    // 入力ファイル名
    private String inName;

    // 出力ファイル名
    private String outName;

    // 画像格納クラス
    private BufferedImage img = null;

    public SquareFileCreator(String inName,
                              String outName) {
        this.inName = inName;
        this.outName = outName;
    }

    public void createSqueare()
            throws IOException {

        this.img = ImageIO.read(new File(this.inName));
        this.width = img.getWidth();
        this.height = img.getHeight();

        int x = (this.width)/2;
        int y = (this.height)/2;

        BufferedImage output = null;

        if (this.width > this.height) {
            output = this.img.getSubimage(x,
                                           y,
                                           (this.height/2),
                                           (this.height/2));
        } else {
            output = this.img.getSubimage(x,
                    y,
                    (this.width/2),
                    (this.width/2));
        }
        ImageIO.write(output, "JPG", new File(outName));

        // BufferedImageをflushする。
        output.flush();
        img.flush();
    }

}
