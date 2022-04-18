package com.fotografcim.service;

import com.fotografcim.domain.Album;
import com.fotografcim.domain.User;
import com.fotografcim.repository.AlbumRepository;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import tech.jhipster.config.JHipsterProperties;

/**
 * Service for sending emails.
 * <p>
 * We use the {@link Async} annotation to send emails asynchronously.
 */
@Service
public class MailService {

    private final Logger log = LoggerFactory.getLogger(MailService.class);

    private static final String USER = "user";

    private static final String ALBUM = "album";

    private static final String BASE_URL = "baseUrl";

    private final JHipsterProperties jHipsterProperties;

    private final JavaMailSender javaMailSender;

    private final MessageSource messageSource;

    private final SpringTemplateEngine templateEngine;

    private final AlbumRepository albumRepository;

    public MailService(
        JHipsterProperties jHipsterProperties,
        JavaMailSender javaMailSender,
        MessageSource messageSource,
        SpringTemplateEngine templateEngine,
        AlbumRepository albumRepository
    ) {
        this.jHipsterProperties = jHipsterProperties;
        this.javaMailSender = javaMailSender;
        this.messageSource = messageSource;
        this.templateEngine = templateEngine;
        this.albumRepository = albumRepository;
    }

    @Async
    public void sendEmail(String to, String subject, String content, boolean isMultipart, boolean isHtml) {
        log.debug(
            "Send email[multipart '{}' and html '{}'] to '{}' with subject '{}' and content={}",
            isMultipart,
            isHtml,
            to,
            subject,
            content
        );

        // Prepare message using a Spring helper
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, isMultipart, StandardCharsets.UTF_8.name());
            message.setTo(to);
            message.setFrom(jHipsterProperties.getMail().getFrom());
            message.setSubject(subject);
            message.setText(content, isHtml);
            javaMailSender.send(mimeMessage);
            log.debug("Sent email to User '{}'", to);
        } catch (MailException | MessagingException e) {
            log.warn("Email could not be sent to user '{}'", to, e);
        }
    }

    @Async
    public void sendEmailFromTemplate(User user, String templateName, String titleKey) {
        if (user.getEmail() == null) {
            log.debug("Email doesn't exist for user '{}'", user.getLogin());
            return;
        }
        Locale locale = Locale.forLanguageTag(user.getLangKey());
        Context context = new Context(locale);
        context.setVariable(USER, user);
        context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());
        String content = templateEngine.process(templateName, context);
        String subject = messageSource.getMessage(titleKey, null, locale);
        sendEmail(user.getEmail(), subject, content, false, true);
    }

    @Async
    public void sendTokenEmailFromTemplate(User user, Album album, String templateName, String titleKey) {
        if (album.getClient().getClientMail() == null) {
            log.debug("Email doesn't exist for client '{}'", album.getClient());
            return;
        }
        Locale locale = Locale.forLanguageTag(user.getLangKey());
        Context context = new Context(locale);
        context.setVariable(USER, user);
        context.setVariable(ALBUM, album);
        context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());
        String content = templateEngine.process(templateName, context);
        String subject = messageSource.getMessage(titleKey, null, locale);
        sendEmail(album.getClient().getClientMail(), subject, content, false, true);
    }

    @Async
    public void sendActivationEmail(User user) {
        log.debug("Sending activation email to '{}'", user.getEmail());
        sendEmailFromTemplate(user, "mail/activationEmail", "email.activation.title");
    }

    @Async
    public void sendCreationEmail(User user) {
        log.debug("Sending creation email to '{}'", user.getEmail());
        sendEmailFromTemplate(user, "mail/creationEmail", "email.activation.title");
    }

    @Async
    public void sendPasswordResetMail(User user) {
        log.debug("Sending password reset email to '{}'", user.getEmail());
        sendEmailFromTemplate(user, "mail/passwordResetEmail", "email.reset.title");
    }

    @Async
    public void sendAlbumToken(User user, Album album) {
        log.debug("Sending creation email to '{}'", album.getClient().getClientMail());
        String temp = user.getResetKey();
        user.setResetKey(album.getToken());
        sendTokenEmailFromTemplate(user, album, "mail/albumTokenEmail", "email.albumToken.title");
        user.setResetKey(temp);
    }

    //    @Async
    //    public void createAlbumEmail(String to, String subject, String content, boolean isMultipart, boolean isHtml) {
    //        log.debug("Send email[multipart '{}' and html '{}'] to '{}' with subject '{}' and content={}",
    //            isMultipart, isHtml, to, subject, content);
    //
    //        // Prepare message using a Spring helper
    //        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
    //        String s = callToken(jHipsterProperties.getMail().getFrom());
    //        try {
    //            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, isMultipart, StandardCharsets.UTF_8.name());
    //            message.setTo(to);
    //            message.setFrom(jHipsterProperties.getMail().getFrom());
    //            message.setSubject(subject);
    //            message.setText(content+s, isHtml);
    //            javaMailSender.send(mimeMessage);
    //            log.debug("Sent email to User '{}'", to);
    //        }  catch (MailException | MessagingException e) {
    //            log.warn("Email could not be sent to user '{}'", to, e);
    //        }
    //    }

    public String callToken(String mail) {
        // create a string of uppercase and lowercase characters and numbers
        String alphabet = "abcdefghijklmnoprstuvyzxwq";
        String upperAlphabet = alphabet.toUpperCase();
        String lowerAlphabet = alphabet.toLowerCase();
        String numbers = "0123456789";
        String special = "-*+!$#&";

        // combine all strings
        String alphaNumeric = upperAlphabet + special + numbers + lowerAlphabet;

        // create random string builder
        StringBuilder sb = new StringBuilder();

        // create an object of Random class
        Random random = new Random();

        // create an object of Date class
        Date date = new Date();

        long time = date.getTime();
        System.out.println(time);

        // specify length of random string
        int length = 20;

        for (int i = 0; i < length; i++) {
            // generate random index number
            int index = random.nextInt(alphaNumeric.length());

            // get character specified by index
            // from the string
            char randomChar = alphaNumeric.charAt(index);

            // append the character to string builder
            sb.append(randomChar);
        }

        String randomString = sb.toString() + time;
        System.out.println("Random Token is: " + randomString);

        return randomString;
    }
}
