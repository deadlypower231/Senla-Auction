package eu.senla.auction.trading.api.mappers;

import eu.senla.auction.trading.api.dto.lot.*;
import eu.senla.auction.trading.entity.entities.Lot;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@UtilityClass
public class LotMapper {

    public Lot mapCreateLot(CreateLotDto source) {
        return (source == null) ? null : Lot.builder()
                .name(source.getName())
                .description(source.getDescription())
                .dateEnd(LocalDateTime.parse(source.getDateEnd().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)))
                .dateStart(source.getDateStart())
                .build();
    }

    public LotDto mapLotDto(Lot source) {
        return (source == null) ? null : LotDto.builder()
                .id(source.getId().toString())
                .name(source.getName())
                .price(source.getPrice())
                .description(source.getDescription())
                .dateStart(source.getDateStart().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .dateEnd(source.getDateEnd().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .build();
    }

    public LotActiveDto mapLotActiveDto(Lot source) {
        return (source == null) ? null : LotActiveDto.builder()
                .id(source.getId().toString())
                .name(source.getName())
                .price(source.getPrice())
                .description(source.getDescription())
                .dateStart(source.getDateStart().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .dateEnd(source.getDateEnd().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .build();
    }

    public LotCompletedDto mapLotCompletedDto(Lot source) {
        return (source == null) ? null : LotCompletedDto.builder()
                .name(source.getName())
                .price(source.getPrice())
                .description(source.getDescription())
                .dateStart(source.getDateStart().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .dateEnd(source.getDateEnd().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .userWin((source.getUserWin() == null) ? null : source.getUserWin().toString())
                .build();
    }

    public LotCompletedCurrentUserDto mapLotCompletedCurrentUserDto(Lot source) {
        return (source == null) ? null : LotCompletedCurrentUserDto.builder()
                .id(source.getId().toString())
                .name(source.getName())
                .price(source.getPrice())
                .description(source.getDescription())
                .dateStart(source.getDateStart().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .dateEnd(source.getDateEnd().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .userWin((source.getUserWin() == null) ? null : source.getUserWin().toString())
//                .chat((source.getChat() == null) ? null : source.getChat().toString())
                .chat(String.valueOf(Optional.ofNullable(source.getChat()).orElse(null)))
                .build();
    }

    public LotInactiveDto mapLotInactiveDto(Lot source) {
        return (source == null) ? null : LotInactiveDto.builder()
                .name(source.getName())
                .price(source.getPrice())
                .description(source.getDescription())
                .dateStart(source.getDateStart().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .dateEnd(source.getDateEnd().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .build();
    }

    public List<LotDto> mapLotsDto(List<Lot> source) {
        return source.stream().map(LotMapper::mapLotDto).collect(Collectors.toList());
    }

    public List<LotInactiveDto> mapLotsInactiveDto(List<Lot> source) {
        return source.stream().map(LotMapper::mapLotInactiveDto).collect(Collectors.toList());
    }

    public List<LotCompletedDto> mapLotsCompletedDto(List<Lot> source) {
        return source.stream().map(LotMapper::mapLotCompletedDto).collect(Collectors.toList());
    }
    public List<LotCompletedCurrentUserDto> mapLotsCompletedCurrentUserDto(List<Lot> source) {
        return source.stream().map(LotMapper::mapLotCompletedCurrentUserDto).collect(Collectors.toList());
    }

    public List<LotActiveDto> mapLotsActiveDto(List<Lot> source) {
        return source.stream().map(LotMapper::mapLotActiveDto).collect(Collectors.toList());
    }

}
