package eu.senla.auction.trading.api.dto;

import eu.senla.auction.trading.entity.entities.Bet;
import eu.senla.auction.trading.entity.entities.User;
import eu.senla.auction.trading.entity.enums.Status;
import lombok.*;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LotDto {

    private String name;
    private String description;
    private LocalDateTime dateStart;
    private LocalDateTime dateEnd;
    private Double price;
    private UserDto userWin;
    private List<BetDto> bets;
    private Status status;

}
