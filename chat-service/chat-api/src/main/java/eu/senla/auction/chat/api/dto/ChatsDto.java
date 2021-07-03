package eu.senla.auction.chat.api.dto;

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
