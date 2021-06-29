package eu.senla.auction.chat.api.dto;

import lombok.*;
import org.bson.types.ObjectId;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateChatDto {

    private String lotId;
    private String buyerEmail;
    private String dealerEmail;

}