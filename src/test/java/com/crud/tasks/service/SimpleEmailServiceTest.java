package com.crud.tasks.service;

import com.crud.tasks.domain.createdTrelloCard.Mail;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SimpleEmailServiceTest {

    @InjectMocks
    private SimpleEmailService simpleEmailService;

    @Mock
    private JavaMailSender javaMailSender;

    @Test
    void shouldSendEmail() {
        //given
        Mail mail = Mail.builder()
                .receiverEmail("test@test.com")
                .toCc("testCc@test.com")
                .subject("test")
                .message("test message")
                .build();
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mail.getReceiverEmail());
        mailMessage.setSubject(mail.getSubject());
        mailMessage.setText(mail.getMessage());
        mailMessage.setCc(mail.getToCc());
        //when
        simpleEmailService.send(mail);
        //then
        verify(javaMailSender, times(1)).send(mailMessage);
    }

    @Test
    void shouldSendEmailWithoutCc() {
        //given
        Mail mail = Mail.builder()
                .receiverEmail("test@test.com")
                .subject("test")
                .message("test message")
                .build();
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mail.getReceiverEmail());
        mailMessage.setSubject(mail.getSubject());
        mailMessage.setText(mail.getMessage());
        //when
        simpleEmailService.send(mail);
        //then
        verify(javaMailSender, times(1)).send(mailMessage);
    }
}