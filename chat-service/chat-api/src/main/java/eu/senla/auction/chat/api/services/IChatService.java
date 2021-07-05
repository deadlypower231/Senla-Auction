package eu.senla.auction.chat.api.services;

import eu.senla.auction.chat.api.dto.ChatDto;
import eu.senla.auction.chat.api.dto.ChatsDto;
import eu.senla.auction.chat.api.dto.CreateChatDto;

import java.util.List;

public interface IChatService {

    ChatDto createChat(CreateChatDto createChatDto);

    ChatsDto getChats(String email);

    List<ChatDto> getChats();

}
