package eu.senla.auction.trading.api.dto.chat;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatViewDto {

    private String id;
    private Map<LocalDateTime, String> messages;

}
