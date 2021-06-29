package eu.senla.auction.chat.api.dto;

import lombok.*;
import org.bson.types.ObjectId;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatDto {

    private String id;
    private String lotId;
    private String dealerEmail;
    private String buyerEmail;
    private List<String> buyerMessages;
    private List<String> dealerMessages;

}
