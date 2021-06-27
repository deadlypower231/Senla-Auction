package eu.senla.auction.trading.api.dto;

import eu.senla.auction.trading.entity.enums.Status;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BetDto {

    private UserDto userDto;
    private Double price;
    private String date;
    private LotDto lotDto;
    private Status status;

}
