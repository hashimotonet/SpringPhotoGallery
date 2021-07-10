/**
 * 
 */
package hashimotonet.controller;

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
  @GetMapping
  public String index(Model model) {
    return "index";
  }
}
