package eu.senla.auction.trading.api.dto.lot;

import eu.senla.auction.trading.api.dto.bet.BetDto;
import eu.senla.auction.trading.api.dto.user.UserDto;
import lombok.*;
import org.bson.types.ObjectId;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LotDto {

    private String id;
    private String name;
    private String description;
    private String dateStart;
    private String dateEnd;
    private Double price;
    private String userWin;
    private List<String> bets;

}
