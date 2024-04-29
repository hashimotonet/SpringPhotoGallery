package hashimotonet.bean;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.ToString;

/**
 * 要求電文をPOJOに置き換えたクラスです。
 *
 * @author Osamu Hashimoto
 *
 */
@Data
@Component
@ToString
public class RequestBean implements Serializable {

	private String id;
	
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
