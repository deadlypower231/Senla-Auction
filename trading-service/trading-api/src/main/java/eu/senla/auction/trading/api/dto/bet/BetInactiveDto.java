package eu.senla.auction.trading.api.dto.bet;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BetInactiveDto {

    private String id;
    private Double price;
    private String dateTime;
    private String lotId;

}
