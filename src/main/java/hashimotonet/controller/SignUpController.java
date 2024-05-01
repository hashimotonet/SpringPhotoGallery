package hashimotonet.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import hashimotonet.mail.MailSendRepository;
import hashimotonet.model.UserAccount;
import hashimotonet.service.SignUpService;

@Controller
@SessionAttributes(types = {UserAccount.class}, names= {"userAccount"})
@RequestMapping
public class SignUpController {
	
    Logger log = (Logger) LogManager.getLogger(SignUpController.class);
    
    @Autowired
    SignUpService service;
    
	@Autowired
	MailSendRepository mailRepo;

	@Autowired
	HttpServletRequest request;
	
	public SignUpController() {
		super();
	}

    @ModelAttribute("userAccount")
    public UserAccount userAccount() {
    	return new UserAccount();
    }

	@PostMapping("/SignUp")
	public String index(@ModelAttribute UserAccount userAccount, HttpSession session) {
		String email = request.getParameter("email");
		String url = "https://google.com/";
		
		boolean success = service.execute(email);
		if (success) {
			mailRepo.sendRegistMail(url, email);
			return "mailSent";
		} else {
			return "addressDuplicated";
		}
	}
}
