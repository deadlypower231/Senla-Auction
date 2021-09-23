package eu.senla.auction.chat.api.mappers;

import eu.senla.auction.chat.api.dto.MessagesDto;
import eu.senla.auction.chat.entity.entities.Chat;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MessageMapper {

    public MessagesDto mapMessagesDto(Chat source){
        return MessagesDto.builder()
                .chatId(source.getId().toString())
                .dealerEmail(source.getDealerEmail())
                .buyerEmail(source.getBuyerEmail())
                .build();
    }

}
