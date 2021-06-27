package eu.senla.auction.trading.api.dto.lot;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateLotDto {

    private String name;
    private String description;
    private LocalDateTime dateEnd;
    private LocalDateTime dateStart;

}
