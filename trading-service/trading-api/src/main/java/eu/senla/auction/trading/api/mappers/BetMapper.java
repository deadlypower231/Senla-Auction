package eu.senla.auction.trading.api.mappers;

import eu.senla.auction.trading.api.dto.bet.BetDto;
import eu.senla.auction.trading.api.dto.bet.CreateBetDto;
import eu.senla.auction.trading.entity.entities.Bet;
import lombok.experimental.UtilityClass;

@UtilityClass
public class BetMapper {

    public Bet mapCreateBet(CreateBetDto source) {
        return Bet.builder()
                .lot(source.getLot())
                .price(source.getPrice())
                .build();
    }

}
