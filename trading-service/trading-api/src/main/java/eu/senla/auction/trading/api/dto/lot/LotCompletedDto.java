package eu.senla.auction.trading.api.dto.lot;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LotCompletedDto {

    private String name;
    private String description;
    private String dateStart;
    private String dateEnd;
    private Double price;
    private String userWin;

}
