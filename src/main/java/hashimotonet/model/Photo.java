package hashimotonet.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Photo {
	
	/**
	 * id
	 */
	private int id;

    /**
     * ユーザの識別子
     */
    private String identity;

    /**
     * ユーザの権限
     */
    private int authority;

    /**
     * 画像データ
     */
    private byte[] data;

    /**
     * サムネイル画像データ
     */
    private byte[] thumbnail;
    
    /**
     * alt テキスト
     */
    private String alt;

    /**
     * デフォルトコンストラクタ
     */
    public Photo() {
    	super();
    }
}
