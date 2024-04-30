package hashimotonet.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SignUpService {

	/**
	 * Logger.
	 */
    private Logger log = LogManager.getLogger(SignUpService.class);
    
    public boolean execute() {
    	boolean result = true;
    	
    	/**
    	 * 基本的に、ユーザ登録のためのサービスを書きます。
    	 * 
    	 * 	・メールアドレスがDB上で重複していないか
    	 * 
    	 *  上記などを検証し、結果OKであれば、true を返却する。
    	 */
    	// TODO 実装する
    	
    	return result;
    }
    
}
