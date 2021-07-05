package eu.senla.auction.chat.api.mappers;

import eu.senla.auction.chat.api.dto.ChatDto;
import eu.senla.auction.chat.api.dto.CreateChatDto;
import eu.senla.auction.chat.entity.entities.Chat;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class ChatMapper {

    public Chat mapCreateChat(CreateChatDto source) {
        return Chat.builder()
                .lotId(source.getLotId())
                .buyerEmail(source.getBuyerEmail())
                .dealerEmail(source.getDealerEmail())
                .build();
    }

    public ChatDto mapChatDto(Chat source) {
        return ChatDto.builder()
                .id(source.getId().toString())
                .lotId(source.getLotId())
                .buyerEmail(source.getBuyerEmail())
                .dealerEmail(source.getDealerEmail())
                .buyerMessages((source.getBuyerMessages() != null) ?
                        source.getBuyerMessages().stream().map(String::valueOf).collect(Collectors.toList()) :
                        new ArrayList<>())
                .dealerMessages((source.getDealerMessages() != null) ?
                        source.getDealerMessages().stream().map(String::valueOf).collect(Collectors.toList()) :
                        new ArrayList<>())
                .build();
    }

    public List<ChatDto> mapChatsDto(List<Chat> source){
        return source.stream().map(ChatMapper::mapChatDto).collect(Collectors.toList());
    }

}
