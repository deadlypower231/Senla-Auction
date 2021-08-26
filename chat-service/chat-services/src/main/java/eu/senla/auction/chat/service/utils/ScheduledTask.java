package eu.senla.auction.chat.service.utils;

import eu.senla.auction.chat.api.repository.ChatRepository;
import eu.senla.auction.chat.api.repository.MessageRepository;
import eu.senla.auction.chat.api.utils.IEmailSender;
import eu.senla.auction.chat.api.utils.IScheduledTask;
import eu.senla.auction.chat.entity.entities.Chat;
import eu.senla.auction.chat.entity.entities.Message;
import eu.senla.auction.chat.entity.enums.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class ScheduledTask implements IScheduledTask {

    private final IEmailSender emailSender;
    private final ChatRepository chatRepository;
    private final MessageRepository messageRepository;

    public ScheduledTask(IEmailSender emailSender, ChatRepository chatRepository, MessageRepository messageRepository) {
        this.emailSender = emailSender;
        this.chatRepository = chatRepository;
        this.messageRepository = messageRepository;
    }

    public Callable<String> sendNotification(String email, Message message, String chatId) {
        return () -> {
            for (Thread t : Thread.getAllStackTraces().keySet()) {
                if (t.getName().equalsIgnoreCase(email + chatId)) {
                    t.interrupt();
                }
            }
            Thread.currentThread().setName(email + chatId);
            Thread.sleep(TimeUnit.HOURS.toMillis(1));
            try {
                message.setNotificationStatus(Status.SENT);
                this.messageRepository.save(message);
                Chat chat = this.chatRepository.findById(chatId);
                if (email.equalsIgnoreCase(chat.getBuyerEmail())) {
                    this.emailSender.sendNotificationEmail(chat.getDealerEmail(), chatId);
                } else {
                    this.emailSender.sendNotificationEmail(chat.getBuyerEmail(), chatId);
                }
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                log.info(e.getMessage());
            }
            return Thread.currentThread().getName();
        };
    }

}
