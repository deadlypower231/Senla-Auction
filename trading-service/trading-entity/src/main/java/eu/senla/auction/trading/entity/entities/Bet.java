package eu.senla.auction.trading.entity.entities;

import eu.senla.auction.trading.entity.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Document(collection = "bet")
public class Bet extends AEntity<ObjectId> {

    private User user;
    private Double price;
    private String date;
    private Lot lot;
    private Status status;

}
