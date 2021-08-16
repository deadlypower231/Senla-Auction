package eu.senla.auction.trading.api.providers;

import eu.senla.auction.trading.api.dto.chat.ChatMessageDto;
import eu.senla.auction.trading.api.dto.chat.ChatsDto;
import eu.senla.auction.trading.api.dto.chat.MessagesDto;
import eu.senla.auction.trading.api.dto.chat.SendMessageDto;
import org.springframework.http.ResponseEntity;

public interface IChatProviders {

    ResponseEntity<MessagesDto> getChat(ChatMessageDto chatMessageDto);

    ResponseEntity<ChatsDto> getChats(String email);

    ResponseEntity<MessagesDto> sendMessages(SendMessageDto sendMessageDto);

}
