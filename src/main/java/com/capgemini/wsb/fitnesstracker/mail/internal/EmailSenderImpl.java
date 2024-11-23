package com.capgemini.wsb.fitnesstracker.mail.internal;

import com.capgemini.wsb.fitnesstracker.mail.api.EmailDto;
import com.capgemini.wsb.fitnesstracker.mail.api.EmailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link EmailSender} interface.
 */
@RequiredArgsConstructor
@Service
public class EmailSenderImpl implements EmailSender {

    /**
     * JavaMailSender instance used to send emails.
     */
    private final JavaMailSender javaMailSender;

    /**
     * Sends the email message to the recipient from the provided {@link EmailDto}.
     *
     * @param email information on email to be sent
     */
    @Override
    public void send(EmailDto email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email.toAddress());
        message.setSubject(email.subject());
        message.setText(email.content());
        javaMailSender.send(message);
    }
}