package hashimotonet.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.web.firewall.RequestRejectedHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import hashimotonet.action.SignInAction;
import hashimotonet.handler.CustomHttpStatusRequestRejectedHandler;
import hashimotonet.model.Account;

/**
 * @author hashi
 *
 */
@Controller
@SessionAttributes(types = {Account.class}, names= {"account"})
@RequestMapping("/")
//@RequestMapping(path="/", method = RequestMethod.POST)
public class SignInController {
	
    Logger log = (Logger) LogManager.getLogger(SignInController.class);
    
	@Autowired
	HttpServletRequest request;

    
    @Autowired
    HttpServletResponse response;
    
    public SignInController() {
    	super();
    }
    
    @ModelAttribute("account")
    public Account account() {
    	return new Account();
    }
    
	@PostMapping("/SignIn")
	public String index(@ModelAttribute Account account, HttpSession session) {
		Integer counter = account.getLoginCount();
		account.setLoginCount(counter++);
		account.setUserId(session.getId());
	 
		SignInAction action = new SignInAction();
		boolean success = false;
		String name = request.getParameter("userName");
		String password = request.getParameter("password");
		
		account.setId(name);
		account.setPassword(password);
		
		session.setAttribute("account", account);
	    
		String referer = request.getHeader("REFERER");
		
		if (referer != null) {
			if (referer.endsWith("ListImages")) {
				success = true;
			}
		}
		
		if (false == success) {

			try {
				
				success = action.execute(request, name, password);
				
			} catch(ClassNotFoundException | IOException | SQLException | URISyntaxException e) {
				
				log.catching(e);
				
			}
		}
	
		if (success) {
			
			response.setStatus(HttpServletResponse.SC_OK);

			return "photo";
			
		} else {
			
			account.setAuth("unauthorized");

			session.setAttribute("account", account);
			
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

			return "index";
		}
	      
	  }
      
	  @Bean
	  public RequestRejectedHandler requestRejectedHandler() {
          return new CustomHttpStatusRequestRejectedHandler();
      }
	
	
	  /*	@PostMapping
		public String index(@RequestParam("userName") String id, @RequestParam("password") String password, Model model) {
		 
			SignInAction action = new SignInAction();
			boolean success = false;
			
			model.addAttribute("id", id);
			model.addAttribute("password", password);
		    
			String referer = request.getHeader("REFERER");
			
			if (referer.endsWith("ListImages")) {
				success = true;
			}
			
			if (false == success) {

				try {
					
					success = action.execute(request, id, password);
					
				} catch(ClassNotFoundException | IOException | SQLException | URISyntaxException e) {
					
					log.catching(e);
					
				}
			}
		
			if (success) {
				
				response.setStatus(HttpServletResponse.SC_OK);

				return "photo";
				
			} else {

				model.addAttribute("auth", "unauthorized");
				
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

				return "index";
			}
		      
		  }
	*/

}
