package eu.senla.auction.trading.api.mappers;

import eu.senla.auction.trading.api.dto.bet.*;
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

    public BetDto mapBetDto(Bet source) {
        return (source == null) ? null : BetDto.builder()
                .lotId(source.getLot().toString())
                .userId(source.getUser().toString())
                .dateTime(source.getDateTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .price(source.getPrice())
                .build();
    }

    public BetWinnerDto mapBetWinnerDto(Bet source) {
        return (source == null) ? null : BetWinnerDto.builder()
                .dateTime(source.getDateTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .price(source.getPrice())
                .lotId(source.getLot().toString())
                .build();
    }

    public BetActiveDto mapBetActiveDto(Bet source) {
        return (source == null) ? null : BetActiveDto.builder()
                .id(source.getId().toString())
                .lotId(source.getLot().toString())
                .dateTime(source.getDateTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .price(source.getPrice())
                .build();
    }
    public BetInactiveDto mapBetInactiveDto(Bet source) {
        return (source == null) ? null : BetInactiveDto.builder()
                .id(source.getId().toString())
                .lotId(source.getLot().toString())
                .dateTime(source.getDateTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .price(source.getPrice())
                .build();
    }

    public List<BetDto> mapBetsDto(List<Bet> source) {
        return source.stream().map(BetMapper::mapBetDto).collect(Collectors.toList());
    }

    public List<BetWinnerDto> mapBetsWinnerDto(List<Bet> source) {
        return source.stream().map(BetMapper::mapBetWinnerDto).collect(Collectors.toList());
    }
    public List<BetActiveDto> mapBetsActiveDto(List<Bet> source) {
        return source.stream().map(BetMapper::mapBetActiveDto).collect(Collectors.toList());
    }
    public List<BetInactiveDto> mapBetsInactiveDto(List<Bet> source) {
        return source.stream().map(BetMapper::mapBetInactiveDto).collect(Collectors.toList());
    }

}
