package hashimotonet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author hashi
 *
 */
@Controller
@RequestMapping(path="SignIn", method = RequestMethod.POST)
public class SignInController {
  @PostMapping
  public String index(@RequestParam("id") String id, Model model) {
	  model.addAttribute("id", id);
      return "photo";
  }
}
