package eu.senla.auction.trading.api.dto.bet;

import eu.senla.auction.trading.api.dto.lot.LotDto;
import eu.senla.auction.trading.api.dto.user.UserDto;
import eu.senla.auction.trading.entity.enums.Status;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BetDto {

    private UserDto userDto;
    private Double price;
    private String dateTime;
    private LotDto lotDto;
    private Status status;

}
