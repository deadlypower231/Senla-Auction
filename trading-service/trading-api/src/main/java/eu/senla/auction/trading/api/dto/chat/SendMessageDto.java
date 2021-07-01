package eu.senla.auction.trading.api.dto.chat;

import lombok.*;

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
