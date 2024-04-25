package hashimotonet.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import hashimotonet.action.ListImagesAction;
import hashimotonet.action.SignInAction;

/**
 * @author hashi
 *
 */
@Controller
@RequestMapping(path="SignIn", method = RequestMethod.POST)
public class SignInController {
	
    Logger log = (Logger) LogManager.getLogger(ListImagesAction.class);
    
	@Autowired
	HttpServletRequest request;

    
    @Autowired
    HttpServletResponse response;
    
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


	@GetMapping
	public String index(Authentication loginUser, Model model) {
	 
		SignInAction action = new SignInAction();
		boolean success = false;
		
		//model.addAttribute("id", loginUser.getName());
		//model.addAttribute("password", loginUser.getPrincipal().get);
		model.addAttribute("id", "hashimoto");
		model.addAttribute("password", "osamu");
	    
		String referer = request.getHeader("REFERER");
		
		if (referer != null) {
			if (referer.endsWith("ListImages")) {
				success = true;
			}
		}
		
		if (false == success) {

			try {
				
				success = action.execute(request, "hashimoto", "osamu");
				
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
}
