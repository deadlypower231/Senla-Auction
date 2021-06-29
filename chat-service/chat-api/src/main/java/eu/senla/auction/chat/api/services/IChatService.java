package eu.senla.auction.chat.api.services;

import eu.senla.auction.chat.api.dto.ChatDto;
import eu.senla.auction.chat.api.dto.CreateChatDto;

public interface IChatService {

    ChatDto createChat(CreateChatDto createChatDto);

}
