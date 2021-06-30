package eu.senla.auction.trading.api.dto.lot;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LotActiveDto {

    private String id;
    private String name;
    private String description;
    private String dateStart;
    private String dateEnd;
    private Double price;

}
