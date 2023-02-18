/**
 *
 */
package hashimotonet.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.io.ClassPathResource;

/**
 * プロパティファイルを扱うユーティリティクラスです。
 *
 * @author Osamu Hashimoto
 *
 */
public final class PropertyUtil {

	/**
	 * プロパティ
	 */
	Properties prop;

	/**
	 * ロガー
	 */
	Logger log = LogManager.getLogger(PropertyUtil.class);

	/**
	 * 当クラスのインスタンスをロードします。
	 */
	public PropertyUtil(String file)
				throws IOException,
						URISyntaxException {
		init(file);
	}

	/**
	 * キー名により値を取得します。
	 *
	 * @param key
	 * @return
	 */
	public String get(String key) {
		String value = this.prop.getProperty(key);

		log.trace("value = " + value);

		return value;
	}

	/**
	 * 初期化処理。
	 * プロパティファイルをロードします。
	 *
	 * @param file
	 * @throws IOException
	 * @throws URISyntaxException
	 *
	 */
	private void init(String file)
				throws IOException {
		InputStream in = getStreamByURL(file);
		prop = new Properties();
		prop.load(in);
	}

	/**
	 * 引数のファイル名をクラスパスリソースから検索し
	 * 取得したURLよりInputStreamを取得して返却します。
	 * 
	 * @param fileName クラスパス資源であるファイル
	 * @return InputStream ファイルの入力ストリーム
	 * @throws IOException 入出力例外
	 */
	private InputStream getStreamByURL(String fileName) throws IOException {
		InputStream in = null;
		
		System.out.println("fileName : " + fileName);
		
		// 当クラスからクラスローダを取得し、クラスパス資源のURLを求める
		//URL url = this.getClass().getClassLoader().getResource(fileName);
		
		//in = url.openStream();
		in = new ClassPathResource(fileName).getInputStream();

		return in;
	}

}
