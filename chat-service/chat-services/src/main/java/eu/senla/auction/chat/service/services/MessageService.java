package eu.senla.auction.chat.service.services;

import eu.senla.auction.chat.api.dto.ChatMessageDto;
import eu.senla.auction.chat.api.dto.MessagesDto;
import eu.senla.auction.chat.api.dto.SendMessageDto;
import eu.senla.auction.chat.api.exceptions.NoAccessException;
import eu.senla.auction.chat.api.exceptions.NullPointerHandException;
import eu.senla.auction.chat.api.mappers.MessageMapper;
import eu.senla.auction.chat.api.repository.ChatRepository;
import eu.senla.auction.chat.api.repository.MessageRepository;
import eu.senla.auction.chat.api.services.IMessageService;
import eu.senla.auction.chat.api.utils.IScheduledTask;
import eu.senla.auction.chat.entity.entities.Chat;
import eu.senla.auction.chat.entity.entities.Message;
import eu.senla.auction.chat.entity.enums.Status;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.errors.InterruptException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

@Service
@Slf4j
public class MessageService implements IMessageService {

    private final MessageRepository messageRepository;
    private final ChatRepository chatRepository;
    private final IScheduledTask scheduledTask;
    private final ScheduledThreadPoolExecutor scheduledThreadPoolExecutor;
    private final KafkaProducer<String, String> kafkaProducer;
    private final ExecutorService executorService;

    public MessageService(MessageRepository messageRepository, ChatRepository chatRepository, IScheduledTask scheduledTask, ScheduledThreadPoolExecutor scheduledThreadPoolExecutor, KafkaProducer<String, String> kafkaProducer, ExecutorService executorService) {
        this.messageRepository = messageRepository;
        this.chatRepository = chatRepository;
        this.scheduledTask = scheduledTask;
        this.scheduledThreadPoolExecutor = scheduledThreadPoolExecutor;
        this.kafkaProducer = kafkaProducer;
        this.executorService = executorService;
    }

    @Override
    public MessagesDto sendMessage(SendMessageDto sendMessageDto) {
        Message message = new Message();
        message.setStatus(Status.UNREAD);
        message.setNotificationStatus(Status.UNSENT);
        message.setText(sendMessageDto.getText());
        message.setTimePublication(new Date());
        ProducerRecord<String, String> producer = new ProducerRecord<>(
                sendMessageDto.getChatId(), sendMessageDto.getEmail(), sendMessageDto.getText()
        );
        this.kafkaProducer.send(producer);
        Set<Thread> threads = Thread.getAllStackTraces().keySet();
        for (Thread t : Thread.getAllStackTraces().keySet()) {
            if (t.getName().equalsIgnoreCase(sendMessageDto.getChatId())) {
                return null;
            }
        }
        executorService.submit(consumerChat(sendMessageDto.getChatId()));
        return null;

//        Message savedMessage = this.messageRepository.save(message);
//        this.scheduledThreadPoolExecutor.schedule(scheduledTask.sendNotification(sendMessageDto.getEmail(), message, sendMessageDto.getChatId()), 1, MILLISECONDS);
//        Chat chat = this.chatRepository.findById(sendMessageDto.getChatId());
//        if (chat.getBuyerEmail().equals(sendMessageDto.getEmail())) {
//            changeStatusToRead(chat.getDealerMessages());
//            if (chat.getBuyerMessages() == null) {
//                chat.setBuyerMessages(Collections.singletonList(savedMessage.getId()));
//            } else {
//                chat.getBuyerMessages().add(savedMessage.getId());
//            }
//        } else {
//            changeStatusToRead(chat.getBuyerMessages());
//            if (chat.getDealerMessages() == null) {
//                chat.setDealerMessages(Collections.singletonList(savedMessage.getId()));
//            } else {
//                chat.getDealerMessages().add(savedMessage.getId());
//            }
//        }
//        this.chatRepository.save(chat);
//        MessagesDto messagesDto = MessageMapper.mapMessagesDto(chat);
//        messagesDto.setBuyerMessage(buildMessages(chat.getBuyerMessages()));
//        messagesDto.setDealerMessage(buildMessages(chat.getDealerMessages()));
//        return messagesDto;
    }

    @Override
    public MessagesDto chat(ChatMessageDto chatMessageDto) throws NoAccessException, NullPointerHandException {
        Chat chat = this.chatRepository.findById(chatMessageDto.getChatId());
        try {
            if (chat.getBuyerEmail().equals(chatMessageDto.getEmail()) || chat.getDealerEmail().equals(chatMessageDto.getEmail())) {
                if (chat.getBuyerEmail().equalsIgnoreCase(chatMessageDto.getEmail())) {
                    Thread.getAllStackTraces().keySet().forEach(x -> {
                        if (x.getName().equalsIgnoreCase(chat.getDealerEmail() + chat.getId().toString())) {
                            x.interrupt();
                        }
                    });
                } else {
                    Thread.getAllStackTraces().keySet().forEach(x -> {
                        if (x.getName().equalsIgnoreCase(chat.getBuyerEmail() + chat.getId().toString())) {
                            x.interrupt();
                        }
                    });
                }
                MessagesDto result = MessageMapper.mapMessagesDto(chat);
                changeStatusToRead((chat.getBuyerEmail().equals(chatMessageDto.getEmail()) ? chat.getDealerMessages() : chat.getBuyerMessages()));
                result.setBuyerMessage(buildMessages(chat.getBuyerMessages()));
                result.setDealerMessage(buildMessages(chat.getDealerMessages()));
                return result;
            } else {
                throw new NoAccessException("You don't have access!");
            }
        } catch (NullPointerException e) {
            throw new NullPointerHandException("Does not exist!");
        }

    }

    private void changeStatusToRead(List<ObjectId> ids) {
        if (ids != null) {
            Iterable<Message> messages = this.messageRepository.findAllById(ids);
            messages.forEach(x -> x.setStatus(Status.READ));
            this.messageRepository.saveAll(messages);
        }
    }

    private Map<Date, String> buildMessages(List<ObjectId> ids) {
        if (ids != null) {
            Map<Date, String> messagesMap = new HashMap<>();
            Iterable<Message> messages = this.messageRepository.findAllById(ids);
            messages.forEach(x -> messagesMap.put(x.getTimePublication(), x.getText()));
            return messagesMap;
        }
        return null;
    }

    private Runnable consumerChat(String chatId) throws InterruptException {
        return () -> {
            Thread.currentThread().setName(chatId);
            Properties properties = new Properties();
            properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
            properties.put(ConsumerConfig.GROUP_ID_CONFIG,
                    chatId);
            properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
            properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
            properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
            KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(properties);
            kafkaConsumer.subscribe(Arrays.asList(chatId));

            try {
                while (true) {
                    ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofMillis(100));
                    for (ConsumerRecord<String, String> record : records) {
                        System.out.println(Thread.currentThread().getName() + " " + record.topic() + " " + new Date(record.timestamp()) + " " + record.key() + " " + record.value());
                    }
                }
            } finally {
                kafkaConsumer.close();
            }
        };
    }


}
