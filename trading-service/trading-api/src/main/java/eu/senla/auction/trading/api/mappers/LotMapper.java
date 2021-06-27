package eu.senla.auction.trading.api.mappers;

import eu.senla.auction.trading.api.dto.CreateLotDto;
import eu.senla.auction.trading.api.dto.LotDto;
import eu.senla.auction.trading.entity.entities.Lot;
import lombok.experimental.UtilityClass;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

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

    public LotDto mapLotDto(Lot source){
        return LotDto.builder()
                .name(source.getName())
                .price(source.getPrice())
                .description(source.getDescription())
                .dateStart(source.getDateStart())
                .dateEnd(source.getDateEnd())
                .userWin(Optional.ofNullable(UserMapper.mapUserDto(source.getUserWin())).orElse(null))
                .build();
    }

}
