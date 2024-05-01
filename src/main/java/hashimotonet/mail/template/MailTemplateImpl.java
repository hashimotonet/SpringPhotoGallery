package hashimotonet.mail.template;

import java.util.HashMap;
import java.util.Map;

import hashimotonet.mail.config.MailTemplateConfig;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.val;

@RequiredArgsConstructor
public class MailTemplateImpl extends MailTemplateConfig implements MailTemplate {
    // テンプレートパス
    private static final String TEMPLATE_PATH = "/mail/mail_format.vm";

    // テンプレート変数
    /** タイトル */
    @Setter
    private String title;
    
    /** 内容 */
    @Setter
    private String url;
    
    public String getTemplatePath() {
        return TEMPLATE_PATH;
    }

    public Map<String, Object> getVariables() {
        val variables = new HashMap<String, Object>();
        variables.put("url", url);
        return variables;
    }

	public MailTemplateImpl(String url) {
		this.url = url;
	}
}
