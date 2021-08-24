package eu.senla.auction.chat.service.services;

import eu.senla.auction.chat.api.dto.ChatMessageDto;
import eu.senla.auction.chat.api.dto.MessagesDto;
import eu.senla.auction.chat.api.dto.SendMessageDto;
import eu.senla.auction.chat.api.exceptions.NoAccess;
import eu.senla.auction.chat.api.exceptions.NullPointerExceptionHand;
import eu.senla.auction.chat.api.mappers.MessageMapper;
import eu.senla.auction.chat.api.repository.ChatRepository;
import eu.senla.auction.chat.api.repository.MessageRepository;
import eu.senla.auction.chat.api.services.IMessageService;
import eu.senla.auction.chat.api.utils.IScheduledTask;
import eu.senla.auction.chat.entity.entities.Chat;
import eu.senla.auction.chat.entity.entities.Message;
import eu.senla.auction.chat.entity.enums.Status;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import static java.util.concurrent.TimeUnit.SECONDS;

@Service
@Slf4j
public class MessageService implements IMessageService {

    private final MessageRepository messageRepository;
    private final ChatRepository chatRepository;
    private final IScheduledTask scheduledTask;
    private final ScheduledThreadPoolExecutor threadPoolExecutor = new ScheduledThreadPoolExecutor(Integer.MAX_VALUE);

    public MessageService(MessageRepository messageRepository, ChatRepository chatRepository, IScheduledTask scheduledTask) {
        this.messageRepository = messageRepository;
        this.chatRepository = chatRepository;
        this.scheduledTask = scheduledTask;
    }

    @Override
    public MessagesDto sendMessage(SendMessageDto sendMessageDto) {
        Message message = new Message();
        message.setStatus(Status.UNREAD);
        message.setNotificationStatus(Status.UNSENT);
        message.setText(sendMessageDto.getText());
        message.setTimePublication(LocalDateTime.now());
        Message savedMessage = this.messageRepository.save(message);
        this.threadPoolExecutor.schedule(scheduledTask.sendNotification(sendMessageDto.getEmail(), message, sendMessageDto.getChatId()), 1, SECONDS);
        Chat chat = this.chatRepository.findById(sendMessageDto.getChatId());
        if (chat.getBuyerEmail().equals(sendMessageDto.getEmail())) {
            changeStatusToRead(chat.getDealerMessages());
            if (chat.getBuyerMessages() == null) {
                chat.setBuyerMessages(Collections.singletonList(savedMessage.getId()));
            } else {
                chat.getBuyerMessages().add(savedMessage.getId());
            }
        } else {
            changeStatusToRead(chat.getBuyerMessages());
            if (chat.getDealerMessages() == null) {
                chat.setDealerMessages(Collections.singletonList(savedMessage.getId()));
            } else {
                chat.getDealerMessages().add(savedMessage.getId());
            }
        }
        this.chatRepository.save(chat);
        MessagesDto messagesDto = MessageMapper.mapMessagesDto(chat);
        messagesDto.setBuyerMessage(buildMessages(chat.getBuyerMessages()));
        messagesDto.setDealerMessage(buildMessages(chat.getDealerMessages()));


        return messagesDto;
    }

    @Override
    public MessagesDto chat(ChatMessageDto chatMessageDto) throws NoAccess, NullPointerExceptionHand {
        Chat chat = this.chatRepository.findById(chatMessageDto.getChatId());
        try {
            if (chat.getBuyerEmail().equals(chatMessageDto.getEmail()) || chat.getDealerEmail().equals(chatMessageDto.getEmail())) {
                MessagesDto result = MessageMapper.mapMessagesDto(chat);
                changeStatusToRead((chat.getBuyerEmail().equals(chatMessageDto.getEmail()) ? chat.getDealerMessages() : chat.getBuyerMessages()));
                result.setBuyerMessage(buildMessages(chat.getBuyerMessages()));
                result.setDealerMessage(buildMessages(chat.getDealerMessages()));
                return result;
            } else {
                throw new NoAccess("You don't have access!");
            }
        } catch (NullPointerException e) {
            throw new NullPointerExceptionHand("Does not exist!");
        }

    }

    private void changeStatusToRead(List<ObjectId> ids) {
        if (ids != null) {
            Iterable<Message> messages = this.messageRepository.findAllById(ids);
            messages.forEach(x -> x.setStatus(Status.READ));
            this.messageRepository.saveAll(messages);
        }
    }

    private Map<LocalDateTime, String> buildMessages(List<ObjectId> ids) {
        if (ids != null) {
            Map<LocalDateTime, String> messagesMap = new HashMap<>();
            Iterable<Message> messages = this.messageRepository.findAllById(ids);
            messages.forEach(x -> messagesMap.put(x.getTimePublication(), x.getText()));
            return messagesMap;
        }
        return null;
    }

}
