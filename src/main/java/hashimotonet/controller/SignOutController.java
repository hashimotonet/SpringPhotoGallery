/**
 * 
 */
package hashimotonet.controller;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author hashi
 *
 */
@Controller
public final class SignOutController {

	@Autowired
	HttpSession session;
	

    Logger log = (Logger) LogManager.getLogger(SignOutController.class);
    
    @PostMapping("/SignOut")
	public String signOut() {
		
		session.invalidate();
		
		log.info("サインアウトしました。");
		
		return "redirect:/";
	}

}
