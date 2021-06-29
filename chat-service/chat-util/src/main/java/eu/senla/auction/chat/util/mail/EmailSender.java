package eu.senla.auction.chat.util.mail;

import eu.senla.auction.chat.api.utils.IEmailSender;
import eu.senla.auction.chat.entity.entities.Chat;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.StringWriter;

@Component
public class EmailSender implements IEmailSender {

    private static final String UTF_8 = "UTF-8";
    private static final String FAILED_CONFIGURE_MESSAGE = "Failed configure message {}";

    private final VelocityEngine velocityEngine;
    private final JavaMailSender mailSender;
    @Value("${mail.sender.set.username}")
    private String auctionEmailAddress;

    public EmailSender(VelocityEngine velocityEngine, JavaMailSender mailSender) {
        this.velocityEngine = velocityEngine;
        this.mailSender = mailSender;
    }

    @Async
    @Override
    public void sendEmailToBuyer(Chat chat) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, UTF_8);
        String textBuyer = prepareChatMessageBuyer(chat);
        String subject = "Только что завершился аукцион лота №: " + chat.getLotId();
        try {
            configureMimeMessageHelper(helper, auctionEmailAddress, chat.getBuyerEmail(), textBuyer, subject);
            mailSender.send(message);
        } catch (MailException e) {
//            log.error("Failed to send message to mail, chat id: {}", chat.getId());
        } catch (MessagingException e) {
//            log.error(FAILED_CONFIGURE_MESSAGE, e.getMessage());
        }
    }

    @Async
    @Override
    public void sendEmailToDealer(Chat chat) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, UTF_8);
        String textDealer = prepareChatMessageDealer(chat);
        String subject = "Только что завершился аукцион лота №: " + chat.getLotId();
        try {
            configureMimeMessageHelper(helper, auctionEmailAddress, chat.getDealerEmail(), textDealer, subject);
            mailSender.send(message);
        } catch (MailException e) {
//            log.error("Failed to send message to mail, chat id: {}", chat.getId());
        } catch (MessagingException e) {
//            log.error(FAILED_CONFIGURE_MESSAGE, e.getMessage());
        }
    }

    private String prepareChatMessageBuyer(Chat chat) {
        VelocityContext context = creatVelocityWithBasicParameters(chat);
        StringWriter stringWriter = new StringWriter();
        velocityEngine.mergeTemplate("templates/mail/chatMailBuyer.vm", UTF_8, context, stringWriter);
        return stringWriter.toString();
    }

    private String prepareChatMessageDealer(Chat chat) {
        VelocityContext context = creatVelocityWithBasicParameters(chat);
        StringWriter stringWriter = new StringWriter();
        velocityEngine.mergeTemplate("templates/mail/chatMailDealer.vm", UTF_8, context, stringWriter);
        return stringWriter.toString();
    }

    private VelocityContext creatVelocityWithBasicParameters(Chat chat) {
        VelocityContext context = new VelocityContext();
        context.put("lot", chat.getLotId());
        return context;
    }

    private void configureMimeMessageHelper(MimeMessageHelper helper, String mailFrom, String mailTo, String mailText,
                                            String mailSubject) throws MessagingException {
        helper.setFrom(mailFrom);
        helper.setTo(mailTo);
        helper.setText(mailText, true);
        helper.setSubject(mailSubject);
    }

}