package hashimotonet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication(scanBasePackages={"hashimotonet"})
public class SpringPhotoGalleryApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(SpringPhotoGalleryApplication.class, args);
	}
	
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
       return application.sources(SpringPhotoGalleryApplication.class);
    }
}
