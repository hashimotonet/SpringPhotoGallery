package hashimotonet.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import hashimotonet.service.ListImagesService;

@Controller
@RequestMapping(path="/ListImages", method = RequestMethod.GET)
@SessionAttributes(value="images")
public class ListController {
	
	@Autowired
	private HttpServletRequest request;
	
	/**
	 * 画像データ操作サービス
	 */
	private final ListImagesService service;
	
	/**
	 * Logger.
	 */
    private Logger log = LogManager.getLogger(ListController.class);

    /**
     * DIコンストラクタ
     * 
     * @param service
     */
	public ListController(ListImagesService service) {
		this.service = service;
	}

	@ModelAttribute(value="images")
	public String setUpImages() {
		return new String();
	}
	

	@PostMapping
	public String index(@RequestParam("id") String id, @ModelAttribute("images") String images, Model model) {
//		public String index(@RequestParam("id") String id,@RequestParam("password") String password, Model model) {
		model.addAttribute("id", id);
		//model.addAttribute("password", password);
		// String images = "";
		try {
			images = service.execute(request, id);
			model.addAttribute("images", images);
		} catch (ClassNotFoundException | SQLException | IOException | URISyntaxException | ServletException e) {
			log.catching(e);
		}
	    return "display";
	  }

}
