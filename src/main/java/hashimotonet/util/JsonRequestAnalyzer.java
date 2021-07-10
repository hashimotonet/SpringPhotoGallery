/**
 * 
 */
package hashimotonet.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import hashimotonet.bean.RequestBean;

/**
 * @author hashi
 *
 */
public class JsonRequestAnalyzer {

	/**
	 * デフォルトコンストラクタ
	 */
	public JsonRequestAnalyzer() {
		super();
	}
	
	public static RequestBean parse(String json) throws JsonMappingException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		
		RequestBean bean = mapper.readValue(json, RequestBean.class);
		return bean;
	}

}
