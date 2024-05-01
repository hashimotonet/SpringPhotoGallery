package hashimotonet.mail;

import java.util.List;

import org.springframework.mail.SimpleMailMessage;

public class MailMessageBuilder {
    private final SimpleMailMessage mailMessage = new SimpleMailMessage();

    /**
     * 差出人を設定します。
     * @param from 差出人
     * @return {@link MailMessageBuilder}
     */
    public MailMessageBuilder from(String from) {
        this.mailMessage.setFrom(from);
        return this;
    }

    /**
     * 宛先を設定します。
     * @param to 宛先
     * @return {@link MailMessageBuilder}
     */
    public MailMessageBuilder to(String to) {
        this.mailMessage.setTo(to);
        return this;
    }

    /**
     * 宛先を設定します。
     * @param to 宛先
     * @return {@link MailMessageBuilder}
     */
    public MailMessageBuilder to(List<String> to) {
        this.mailMessage.setTo(to.toArray(new String[0]));
        return this;
    }

    /**
     * Bccを設定します。
     * @param bcc Bcc
     * @return {@link MailMessageBuilder}
     */
    public MailMessageBuilder bcc(String bcc) {
        this.mailMessage.setBcc(bcc);
        return this;
    }

    /**
     * 件名を設定します。
     * @param subject 件名
     * @return {@link MailMessageBuilder}
     */
    public MailMessageBuilder subject(String subject) {
        this.mailMessage.setSubject(subject);
        return this;
    }

    /**
     * 本文を設定します。
     * @param content 本文
     * @return {@link MailMessageBuilder}
     */
    public MailMessageBuilder content(String content) {
        this.mailMessage.setText(content);
        return this;
    }

    /**
     * ビルドしたメールを取得します。
     * @return {@link SimpleMailMessage}
     */
    public SimpleMailMessage build() {
        return mailMessage;
    }

}
