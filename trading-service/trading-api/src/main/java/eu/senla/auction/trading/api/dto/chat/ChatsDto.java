package eu.senla.auction.trading.api.dto.chat;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatsDto {

    private String email;
    private List<String> chatsId;

}
