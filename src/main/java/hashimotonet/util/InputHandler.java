/**
 *
 */
package hashimotonet.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * @author user
 *
 */
public class InputHandler {

    /**
     * プライベートコンストラクタ
     */
    private InputHandler() {

    }

    public static String is2String(InputStream inputStream) {
        StringBuilder sb = new StringBuilder();

        try(BufferedReader reader = new BufferedReader(
                new InputStreamReader(inputStream, Charset.forName("UTF-8")))){

            String line;
            while((line = reader.readLine()) != null){
                sb.append(line);
            }
        }catch (IOException | IllegalArgumentException | StringIndexOutOfBoundsException e) {
            e.printStackTrace();
        }

        return sb.toString();

    }

}
