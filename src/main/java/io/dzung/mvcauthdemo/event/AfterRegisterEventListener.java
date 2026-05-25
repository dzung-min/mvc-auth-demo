package io.dzung.mvcauthdemo.event;

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
public class AfterRegisterEventListener {
	private final JavaMailSender mailSender;
	
	@EventListener
	@Async
	public void listen(RegisterEvent event) {
		MimeMessagePreparator messagePreparator = mimeMessage -> {
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
		    messageHelper.setFrom("noreply@mvcauthdemo.dzung.io");
		    messageHelper.setTo(event.getUser().getEmail());
		    messageHelper.setSubject("Email confirmation");
		    String htmlContent = """
		    		<html>
		    			<body>
		    				<h2>Please click the link to complete your registration</h2>
		    				<a href="http://localhost:8080/verify?token=%s">Confirm</a>
		    			</body>
		    		</html>
		    		""".formatted(event.getToken());
		    messageHelper.setText(htmlContent, true);
		};
		try {
			mailSender.send(messagePreparator);
		} catch (MailException e) {
			System.err.print(e.getMessage());
		}
	}
}
