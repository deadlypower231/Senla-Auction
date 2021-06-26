package eu.senla.auction.payment.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Card extends AEntity<ObjectId> {

    @Indexed(unique = true)
    private String numberCard;
    private Double balance;
    private String cvv;

}
