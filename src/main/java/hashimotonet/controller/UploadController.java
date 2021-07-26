/**
 * 
 */
package hashimotonet.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import hashimotonet.bean.RequestBean;
import hashimotonet.service.UploadService;

/**
 * @author hashi
 *
 */
@Controller
public class UploadController {

	@Autowired
	private HttpServletRequest request;

	/**
	 * 画像データ操作サービス
	 */
	private final UploadService service;
	
	/**
	 * Logger.
	 */
    private Logger log = LogManager.getLogger(UploadController.class);

    /**
	 * DIコンストラクタ
	 * 
	 * @param service
	 */
	@Autowired
	public UploadController(UploadService service) {
		this.service = service;
	}
	
	/**
	 * デフォルトコンストラクタ
	 */
	public UploadController() {
		super();
		this.service = new UploadService();
	}

	@RequestMapping(path="Upload", method = RequestMethod.POST)
	public void index(@RequestBody RequestBean reqBean) {
		System.out.println("reqBean : \r\n" + reqBean);
		try {
			service.execute(request, reqBean);
		} catch (Exception e) {
			log.catching(e);
		}
	}
}