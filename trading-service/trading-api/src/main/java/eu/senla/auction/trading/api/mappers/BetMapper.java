package eu.senla.auction.trading.api.mappers;

import eu.senla.auction.trading.api.dto.BetDto;
import eu.senla.auction.trading.entity.entities.Bet;
import lombok.experimental.UtilityClass;

@UtilityClass
public class BetMapper {

    public Bet mapCreateBet(BetDto source) {
        return Bet.builder()
                .date(source.getDate())
                .status(source.getStatus())
                .price(source.getPrice())
                .build();
    }

}
