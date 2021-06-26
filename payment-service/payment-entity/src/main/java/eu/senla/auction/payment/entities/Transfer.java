package eu.senla.auction.payment.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.bson.types.ObjectId;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Transfer extends AEntity<String> {

    private ObjectId lotId;
    private ObjectId dealerId;
    private ObjectId buyerId;
    private Double price;

}
