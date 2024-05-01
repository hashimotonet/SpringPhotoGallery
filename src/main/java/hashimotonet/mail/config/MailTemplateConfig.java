package hashimotonet.mail.config;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import hashimotonet.mail.template.MailTemplate;
import lombok.val;

@Configuration
public class MailTemplateConfig implements MailTemplate {

    private final TemplateMode templateMode = TemplateMode.TEXT;
    private final Charset charset = StandardCharsets.UTF_8;

    /**
     * メールのテンプレートエンジンを使用するための設定インスタンスを生成します。
     * @return {@link ClassLoaderTemplateResolver}
     */
    @Bean(name = "mailTemplateResolver")
    ClassLoaderTemplateResolver mailTemplateResolver() {
        val templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setTemplateMode(templateMode);
        templateResolver.setCharacterEncoding(charset.name());
        return templateResolver;
    }

    /**
     * メールのテンプレートエンジンを使用するためのインスタンスを生成します。
     * @return {@link SpringTemplateEngine}
     */
    @Bean(name = "mailTemplateEngine")
    //    SpringTemplateEngine mailTemplateEngine(@Qualifier("mailTemplateEngine") ClassLoaderTemplateResolver classLoaderTemplateResolver) {
    SpringTemplateEngine mailTemplateEngine(@Qualifier("mailTemplateResolver") ClassLoaderTemplateResolver classLoaderTemplateResolver) {
    	SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(classLoaderTemplateResolver);
        return templateEngine;
    }

	@Override
	public String getTemplatePath() {
		return null;
	}

	@Override
	public Map<String, Object> getVariables() {
		return null;
	}

}
