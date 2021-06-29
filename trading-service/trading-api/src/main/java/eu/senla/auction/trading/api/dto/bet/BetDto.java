package eu.senla.auction.trading.api.dto.bet;

import eu.senla.auction.trading.api.dto.lot.LotDto;
import eu.senla.auction.trading.api.dto.user.UserDto;
import eu.senla.auction.trading.entity.enums.Status;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BetDto {

    private String userId;
    private Double price;
    private String dateTime;
    private String lotId;

}
