package hashimotonet.bean;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 要求電文をPOJOに置き換えたクラスです。
 *
 * @author Osamu Hashimoto
 *
 */
@Setter
@Getter
@ToString
public class RequestBean implements Serializable {

	//private int id;
	
	/**
	 * ユーザID
	 */
	private String identity;

	/**
	 * Base64形式である画像データ
	 */
	private String data;

	/**
	 * altテキスト
	 */
	private String alt;
	
}
