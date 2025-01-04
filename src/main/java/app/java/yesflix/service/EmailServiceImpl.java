package app.java.yesflix.service;

import app.java.yesflix.entity.EmailMessage;
import app.java.yesflix.entity.User;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.util.Properties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService{
    @Value("smtp.gmail.com")
    private String smtpServer;
    @Value("587")
    private Integer smtpPort;
    @Value("danikbern@gmail.com")
    private String systemEmail;
    @Value("jnwiwldfrimetpfg")
    private String systemPassword;

    @Override
    public void sendConfirmEmail(User user) throws MessagingException {
        JavaMailSender javaMailSender = this.getSystemMailSender();
        MimeMessage message = this.prepareMimeMessageBasedOnEmailMessage(createRegistarationEmailMessage(user), javaMailSender);
        javaMailSender.send(message);
    }

    private JavaMailSender getSystemMailSender(){
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(smtpServer);
        mailSender.setPort(smtpPort);
        mailSender.setUsername(systemEmail);
        mailSender.setPassword(systemPassword);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "false");
        return mailSender;
    }

    private MimeMessage prepareMimeMessageBasedOnEmailMessage(EmailMessage emailMessage, JavaMailSender javaMailSender)
            throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(emailMessage.getFrom());
        helper.setTo(emailMessage.getTo());
        helper.setSubject(emailMessage.getSubject());
        helper.setText(emailMessage.getText());
        return message;
    }

    private EmailMessage createRegistarationEmailMessage(User user){
        EmailMessage emailMessage = new EmailMessage();
        emailMessage.setTo(user.getEmail());
        emailMessage.setFrom(systemEmail);
        emailMessage.setSubject("Welcome to yesflix, your account is registered successfully.");
        emailMessage.setText("test");
        return emailMessage;
    }
}
