package eu.senla.auction.trading.api.mappers;

import eu.senla.auction.trading.api.dto.lot.CreateLotDto;
import eu.senla.auction.trading.api.dto.lot.LotDto;
import eu.senla.auction.trading.entity.entities.Lot;
import lombok.experimental.UtilityClass;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@UtilityClass
public class LotMapper {

    public Lot mapCreateLot(CreateLotDto source) {
        return Lot.builder()
                .name(source.getName())
                .description(source.getDescription())
                .dateEnd(source.getDateEnd())
                .dateStart(source.getDateStart())
                .build();
    }

    public LotDto mapLotDto(Lot source) {
        return LotDto.builder()
                .id(source.getId())
                .name(source.getName())
                .price(source.getPrice())
                .description(source.getDescription())
                .dateStart(source.getDateStart().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .dateEnd(source.getDateEnd().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .userWin(Optional.ofNullable(UserMapper.mapUserDto(source.getUserWin())).orElse(null))
                .build();
    }

    public List<LotDto> mapLotsDto(List<Lot> source) {
        return source.stream().map(LotMapper::mapLotDto).collect(Collectors.toList());
    }

}
