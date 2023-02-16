package hashimotonet.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
//import javax.servlet.annotation.WebListener;
import javax.servlet.ServletException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.Banner.Mode;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationListener;

import org.springframework.context.event.ContextStartedEvent;
//import org.springframework.stereotype.Component;
import org.springframework.context.event.EventListener;

import hashimotonet.SpringPhotoGalleryApplication;

/**
 * PhotoGalleryサーバアプリ起動時に実行される初期化リスナークラスです。
 *
* Application Lifecycle Listener implementation class InitListener
 *
 */
//@SpringBootApplication
//@Configuration
public class InitListener extends SpringBootServletInitializer implements ApplicationListener<ContextStartedEvent> {

    /**
     * Logger for this class.
     */
    private Logger log = LogManager.getLogger(InitListener.class);

    /**
     * Default constructor.
     */
    public InitListener() throws ServletException {
        log.info("\n---PhotoGallery Server APP Started.---");
        //new StartupImagesLoader().onStartup() ;
    }

    /**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
    @Override
    public void contextDestroyed(ServletContextEvent sce)  {
    	if (dao != null) {
    		try {
				dao.close();
			} catch (SQLException e) {
				log.catching(e);
			}
    	}
    }
     */

    @Override
    public void onApplicationEvent(ContextStartedEvent evt)  {

    }

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return configureApplication(builder);
	}

	public static void main(String[] args) {
		configureApplication(new SpringApplicationBuilder()).run(args);
	}

    @EventListener(InitListener.class)
	private static SpringApplicationBuilder configureApplication(SpringApplicationBuilder builder) {
		return builder.sources(SpringPhotoGalleryApplication.class).bannerMode(Mode.OFF);
	}
}
