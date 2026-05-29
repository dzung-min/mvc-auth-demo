package io.dzung.mvcauthdemo.event;

import io.dzung.mvcauthdemo.util.exception.TokenType;
import org.springframework.context.event.EventListener;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthEventListener {
	private final JavaMailSender mailSender;
	
	@EventListener
	@Async
	public void listen(AuthEvent event) {
		String path = event.token().getType() == TokenType.VERIFY_REGISTER_EMAIL ? "register-verification" : "forget-password-verification";
		MimeMessagePreparator messagePreparator = mimeMessage -> {
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
		    messageHelper.setFrom("noreply@mvcauthdemo.dzung.io");
		    messageHelper.setTo(event.user().getEmail());
		    messageHelper.setSubject("Email confirmation");
		    String htmlContent = """
		    		<html>
		    			<body>
		    				<h2>Please click the link to complete your registration</h2>
		    				<a href="http://localhost:8080/%s?token=%s">Confirm</a>
		    			</body>
		    		</html>
		    		""".formatted(path, event.token());
		    messageHelper.setText(htmlContent, true);
		};
		try {
			mailSender.send(messagePreparator);
		} catch (MailException e) {
			System.err.print(e.getMessage());
		}
	}
}
