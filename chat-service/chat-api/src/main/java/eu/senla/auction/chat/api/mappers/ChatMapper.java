package eu.senla.auction.chat.api.mappers;

import eu.senla.auction.chat.api.dto.ChatDto;
import eu.senla.auction.chat.api.dto.CreateChatDto;
import eu.senla.auction.chat.entity.entities.Chat;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ChatMapper {

    public Chat mapCreateChat(CreateChatDto source){
        return Chat.builder()
                .lotId(source.getLotId())
                .buyerEmail(source.getBuyerEmail())
                .dealerEmail(source.getDealerEmail())
                .build();
    }

    public ChatDto mapChatDto(Chat source){
        return ChatDto.builder()
                .id(source.getId().toString())
                .lotId(source.getLotId())
                .buyerEmail(source.getBuyerEmail())
                .dealerEmail(source.getDealerEmail())
                .buyerMessages(source.getBuyerMessages())
                .dealerMessages(source.getDealerMessages())
                .build();
    }

}
