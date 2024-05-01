/**
 * 
 */
package hashimotonet.model;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import lombok.Data;
/**
 * 
 */
@Data
@Component
public class Account implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String password;
	private int loginCount;
	private String userId;
	private String auth;
}
