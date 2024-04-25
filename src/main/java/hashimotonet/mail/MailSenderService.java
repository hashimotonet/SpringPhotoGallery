package hashimotonet.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MailSenderService {

	@Autowired
    private MailSender mailSender;

	/**
	 * @javadoc
	 */
	// TODO 送信自体はできているので、後に setText などの文言は拡張することにしておく。
    public void sendMail() {
            var mailInfo = new SimpleMailMessage();
            mailInfo.setSubject("javaの実装練習です");
            mailInfo.setText("お元気ですかテストです");
            mailInfo.setTo("hashimoto.osamu@gmail.com");
            // mailInfo.setFrom("akimbo.himawari0311@gmail.com");

            mailSender.send(mailInfo);

    }
    
	public MailSenderService(MailSender mailsender) {
		this.mailSender = mailsender;
	}
}
