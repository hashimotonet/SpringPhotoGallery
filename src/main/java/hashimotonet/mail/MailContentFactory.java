package hashimotonet.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import hashimotonet.mail.template.MailTemplate;
import lombok.val;

@Component
public class MailContentFactory {
    
	@Autowired
	private SpringTemplateEngine templateEngine;

    /**
     * メール本文を作成します。
     */
    public String create(MailTemplate mailTemplate) {
        return getContent(mailTemplate);
    }

    /**
     * メールのメッセージを取得します。
     * @param mailTemplate メールテンプレート
     * @return メールのメッセージ
     */
    private String getContent(MailTemplate mailTemplate) {
        return templateEngine.process(mailTemplate.getTemplatePath(), getVariables(mailTemplate));
    }

    /**
     * テンプレートのパラメータ情報を取得します。
     * @param mailTemplate メールテンプレート
     * @return {@link Context}
     */
    private Context getVariables(MailTemplate mailTemplate) {
        val context = new Context();
        context.setVariables(mailTemplate.getVariables());
        return context;
    }

    /**
     * コンストラクタ
     * @param templateEngine メールテンプレートエンジン
     */
    private MailContentFactory(@Qualifier("mailTemplateEngine") SpringTemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }
}
