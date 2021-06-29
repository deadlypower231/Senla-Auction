package eu.senla.auction.chat.service.services;

import eu.senla.auction.chat.api.dto.ChatDto;
import eu.senla.auction.chat.api.dto.CreateChatDto;
import eu.senla.auction.chat.api.mappers.ChatMapper;
import eu.senla.auction.chat.api.repository.ChatRepository;
import eu.senla.auction.chat.api.services.IChatService;
import eu.senla.auction.chat.entity.entities.Chat;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@Service
@ComponentScan("eu.senla.auction.chat.api")
public class ChatService implements IChatService {

    private final ChatRepository chatRepository;

    public ChatService(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    @Override
    public ChatDto createChat(CreateChatDto createChatDto) {
        Chat entity = ChatMapper.mapCreateChat(createChatDto);
        Chat chat = this.chatRepository.save(entity);
        return ChatMapper.mapChatDto(chat);
    }
}
