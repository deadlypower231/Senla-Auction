package eu.senla.auction.chat.api.dto;

import lombok.*;
import org.bson.types.ObjectId;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SendMessageDto {

    private String chatId;
    private String text;
    private String email;

}
