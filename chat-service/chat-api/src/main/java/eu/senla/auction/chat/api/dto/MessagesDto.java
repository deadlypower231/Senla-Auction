package eu.senla.auction.chat.api.dto;

import lombok.*;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessagesDto {

    private ObjectId chatId;
    private String buyerEmail;
    private String dealerEmail;
    private Map<LocalDateTime, String> buyerMessage;
    private Map<LocalDateTime, String> dealerMessage;

}
