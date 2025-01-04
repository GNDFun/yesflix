package app.java.yesflix.service;

import app.java.yesflix.entity.User;
import jakarta.mail.MessagingException;

public interface EmailService {
    public void sendConfirmEmail(User user) throws MessagingException;
}
