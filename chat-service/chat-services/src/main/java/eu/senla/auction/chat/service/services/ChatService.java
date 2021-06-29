package eu.senla.auction.chat.service.services;

import eu.senla.auction.chat.api.dto.ChatDto;
import eu.senla.auction.chat.api.dto.CreateChatDto;
import eu.senla.auction.chat.api.mappers.ChatMapper;
import eu.senla.auction.chat.api.repository.ChatRepository;
import eu.senla.auction.chat.api.services.IChatService;
import eu.senla.auction.chat.api.utils.IEmailSender;
import eu.senla.auction.chat.entity.entities.Chat;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@Service
@ComponentScan({"eu.senla.auction.chat.api", "eu.senla.auction.chat.util"})
public class ChatService implements IChatService {

    private final ChatRepository chatRepository;
    private final IEmailSender emailSender;

    public ChatService(ChatRepository chatRepository, IEmailSender emailSender) {
        this.chatRepository = chatRepository;
        this.emailSender = emailSender;
    }

    @Override
    public ChatDto createChat(CreateChatDto createChatDto) {
        Chat entity = ChatMapper.mapCreateChat(createChatDto);
        Chat chat = this.chatRepository.save(entity);
        this.emailSender.sendEmailToBuyer(chat);
        this.emailSender.sendEmailToDealer(chat);
        return ChatMapper.mapChatDto(chat);
    }
}
