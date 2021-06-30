package eu.senla.auction.trading.api.mappers;

import eu.senla.auction.trading.api.dto.bet.BetDto;
import eu.senla.auction.trading.api.dto.bet.CreateBetDto;
import eu.senla.auction.trading.entity.entities.Bet;
import lombok.experimental.UtilityClass;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class BetMapper {

    public Bet mapCreateBet(CreateBetDto source) {
        return Bet.builder()
                .lot(source.getLot())
                .price(source.getPrice())
                .build();
    }

    public BetDto mapBetDto(Bet source){
        return BetDto.builder()
                .lotId(source.getLot().toString())
                .userId(source.getUser().toString())
                .dateTime(source.getDateTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .price(source.getPrice())
                .build();
    }

    public List<BetDto> mapBetsDto(List<Bet> source){
        return source.stream().map(BetMapper::mapBetDto).collect(Collectors.toList());
    }

}
