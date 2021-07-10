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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import hashimotonet.service.ListImagesService;

@Controller
@RequestMapping(path="ListImages", method = RequestMethod.POST)
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
    @Autowired
	public ListController(ListImagesService service) {
		this.service = service;
	}

	@PostMapping
	public String index(@RequestParam("id") String id, Model model) {
		model.addAttribute("id", id);
		try {
			String images = service.execute(request, id);
			model.addAttribute("images", images);
		} catch (ClassNotFoundException | SQLException | IOException | URISyntaxException | ServletException e) {
			log.catching(e);
		}
	    return "display";
	  }

}
