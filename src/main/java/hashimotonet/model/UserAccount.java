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
public class UserAccount implements Serializable {
	private String id;
	private String accountId;
	private String emailAddress;
	private String password;
	private int authority;
	private String telephone1;
}
