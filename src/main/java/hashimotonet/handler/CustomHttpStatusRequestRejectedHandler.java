package hashimotonet.handler;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.log.LogMessage;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.security.web.firewall.RequestRejectedHandler;

public class CustomHttpStatusRequestRejectedHandler implements RequestRejectedHandler {

    private static final Log logger = LogFactory.getLog(CustomHttpStatusRequestRejectedHandler.class);

    private final int httpError;

    /**
     * Constructs an instance which uses {@code 400} as response code.
     */
    public CustomHttpStatusRequestRejectedHandler() {
        this.httpError = HttpServletResponse.SC_BAD_REQUEST;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       RequestRejectedException requestRejectedException) throws IOException {
        logger.info(LogMessage.format("Rejecting request due to: %s", requestRejectedException.getMessage()),	
                requestRejectedException); // ログレベルはINFO
        response.sendError(this.httpError); // 400エラーとしてレスポンスする
    }
}
