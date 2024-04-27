/**
 * 
 */
package hashimotonet.config;

import org.apache.coyote.http11.AbstractHttp11Protocol;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

/**
 * @author hashi
 *
 */
@Slf4j
@Configuration
public class TomcatCustomizer implements WebServerFactoryCustomizer<TomcatServletWebServerFactory> {

    //@Autowired
    //private CacheProperties containerProperties;

    @Override
    public void customize(TomcatServletWebServerFactory factory) {
        factory.addConnectorCustomizers(connector -> {
            AbstractHttp11Protocol protocol = (AbstractHttp11Protocol) connector.getProtocolHandler();

            protocol.setMaxKeepAliveRequests(10);

            //protocol.setSessionCacheSize(1024 * 1024 * 1024);

            
            log.info("####################################################################################");
            log.info("#");
            log.info("# TomcatCustomizer");
            log.info("#");
            log.info("# custom maxKeepAliveRequests {}", protocol.getMaxKeepAliveRequests());
            log.info("# Origin keepalive timeout: {} ms", protocol.getKeepAliveTimeout());
            log.info("# keepalive timeout: {} ms", protocol.getKeepAliveTimeout());
            log.info("# connection timeout: {} ms", protocol.getConnectionTimeout());
            log.info("# max connections: {}", protocol.getMaxConnections());
            //log.info("# session cache size: {}", protocol.getSessionCacheSize());
            log.info("#");
            log.info(
                "####################################################################################");

        });
    }
}