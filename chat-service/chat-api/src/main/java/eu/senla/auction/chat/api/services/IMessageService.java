package eu.senla.auction.chat.api.services;

import eu.senla.auction.chat.api.dto.ChatMessageDto;
import eu.senla.auction.chat.api.dto.MessagesDto;
import eu.senla.auction.chat.api.dto.SendMessageDto;
import eu.senla.auction.chat.api.exceptions.NoAccessException;
import eu.senla.auction.chat.api.exceptions.NullPointerHandException;

public interface IMessageService {

    MessagesDto sendMessage(SendMessageDto sendMessageDto);

    MessagesDto chat(ChatMessageDto chatMessageDto) throws NoAccessException, NullPointerHandException;

}
