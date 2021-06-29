package eu.senla.auction.trading.api.dto.chat;

import lombok.*;
import org.bson.types.ObjectId;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatDto {

    private ObjectId id;
    private ObjectId lotId;
    private String dealerEmail;
    private String buyerEmail;
    private List<String> buyerMessages;
    private List<String> dealerMessages;

}
