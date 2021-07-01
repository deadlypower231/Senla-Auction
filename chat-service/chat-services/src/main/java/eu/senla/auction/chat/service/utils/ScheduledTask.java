package eu.senla.auction.chat.service.utils;

import eu.senla.auction.chat.api.repository.ChatRepository;
import eu.senla.auction.chat.api.repository.MessageRepository;
import eu.senla.auction.chat.api.utils.IEmailSender;
import eu.senla.auction.chat.api.utils.IScheduledTask;
import eu.senla.auction.chat.entity.entities.Chat;
import eu.senla.auction.chat.entity.entities.Message;
import eu.senla.auction.chat.entity.enums.Status;
import org.bson.types.ObjectId;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@EnableScheduling
public class ScheduledTask implements IScheduledTask {

    private final IEmailSender emailSender;
    private final ChatRepository chatRepository;
    private final MessageRepository messageRepository;

    public ScheduledTask(IEmailSender emailSender, ChatRepository chatRepository, MessageRepository messageRepository) {
        this.emailSender = emailSender;
        this.chatRepository = chatRepository;
        this.messageRepository = messageRepository;
    }

    @Scheduled(cron = "* * * * * *")
    @Override
    public void checkMessages() {
        List<Chat> chats = this.chatRepository.findAll();
        for (Chat chat : chats) {
            sendNotification(chat.getBuyerEmail(), chat.getBuyerMessages(), chat.getId().toString());
            sendNotification(chat.getDealerEmail(), chat.getDealerMessages(), chat.getId().toString());
        }
    }

    private void sendNotification(String email, List<ObjectId> messages, String chatId) {
        if (messages != null) {
            List<Message> list = (List<Message>) this.messageRepository.findAllById(messages);
            List<Message> unread =  list.stream().filter(x -> x.getStatus().equals(Status.UNREAD)).collect(Collectors.toList());
            Optional<Message> message = list.stream().max(Comparator.comparing(Message::getTimePublication));
            if (message.get().getTimePublication().plusHours(1).isBefore(LocalDateTime.now()) &&
                    message.get().getNotificationStatus().equals(Status.UNSENT) &&
                    message.get().getStatus().equals(Status.UNREAD)) {
                message.get().setNotificationStatus(Status.SENT);
                this.messageRepository.save(message.get());
                try {
                    this.emailSender.sendNotificationEmail(email, unread.size(), chatId);
                } catch (Exception e) {

                }
            }
        }
    }

}
