/**
 * 
 */
package hashimotonet.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author hashi
 *
 */
@Controller
@RequestMapping("/")
public class IndexController {
	
	Logger log = LogManager.getLogger(IndexController.class);
	
	@Autowired
	HttpServletRequest request;
	
    @GetMapping
    public String index(Model model) {
    	String referer = request.getHeader("REFERER");
    	String param = request.getParameter("param");
    	String page = "";
		log.info(referer);
		log.info(param);
		page = "index";
		if (param != null) {
			switch (param) {
				case "register":
					page = "Register";
					break;
				case "signin":
					page = "SignIn";
					break;
				default:
					page = "display";
					break;
			}
		}
		log.info("page=" + page);
        return page;
    }
}
