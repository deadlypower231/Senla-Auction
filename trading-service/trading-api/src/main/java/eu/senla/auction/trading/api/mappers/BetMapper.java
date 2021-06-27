package eu.senla.auction.trading.api.mappers;

import eu.senla.auction.trading.api.dto.BetDto;
import eu.senla.auction.trading.api.dto.LotDto;
import eu.senla.auction.trading.entity.entities.Bet;
import eu.senla.auction.trading.entity.entities.Lot;
import lombok.experimental.UtilityClass;

@UtilityClass
public class BetMapper {

    public Bet mapCreateBet(BetDto source){
        return Bet.builder()
                .date(source.getDate())
                .status(source.getStatus())
                .price(source.getPrice())
                .build();
    }

    private Lot mapLot(LotDto source){
        return Lot.builder()
                .userWin(UserMapper.mapUser(source.getUserWin()))
                .dateStart(source.getDateStart())
                .dateEnd(source.getDateEnd())
                .build();
    }

}
