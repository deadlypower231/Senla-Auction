package eu.senla.auction.trading.api.dto.bet;

import lombok.*;
import org.bson.types.ObjectId;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateBetDto {

    private ObjectId lot;
    private Double price;

}
