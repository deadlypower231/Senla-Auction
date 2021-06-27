package eu.senla.auction.trading.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

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
