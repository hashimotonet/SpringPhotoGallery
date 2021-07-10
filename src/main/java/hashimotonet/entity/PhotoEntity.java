/**
 * 
 */
package hashimotonet.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author hashi
 *
 */
@Entity
@Table(name = "photo")
@Getter
@Setter
@ToString
public class PhotoEntity implements Example<String> {
	/**
	 * id
	 */
	@Id
	@Column(name = "id")
	//@GeneratedValue(strategy = GenerationType.AUTO)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

    /**
     * ユーザの識別子
     */
	@Column(name = "identity")
    private String identity;

    /**
     * ユーザの権限
     */
	@Column(name = "authority")
    private int authority;

    /**
     * 画像データ
     */
	@Column(name = "data")
    private byte[] data;

    /**
     * サムネイル画像データ
     */
	@Column(name = "thumbnail")
    private byte[] thumbnail;
    
    /**
     * alt テキスト
     */
	@Column(name = "alt")
    private String alt;

    /**
     * デフォルトコンストラクタ
     */
    public PhotoEntity() {

    }

	@Override
	public String getProbe() {
		return this.identity;
	}

	@Override
	public ExampleMatcher getMatcher() {
		return null;
	}
}
