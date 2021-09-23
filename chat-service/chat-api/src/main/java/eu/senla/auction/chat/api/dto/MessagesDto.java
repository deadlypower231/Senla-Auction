package eu.senla.auction.chat.api.dto;

import lombok.*;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessagesDto {

    private String chatId;
    private String buyerEmail;
    private String dealerEmail;
    private Map<Date, String> buyerMessage;
    private Map<Date, String> dealerMessage;

}
