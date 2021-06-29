package eu.senla.auction.trading.entity.entities;

import eu.senla.auction.trading.entity.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Document(collection = "bet")
public class Bet extends AEntity<ObjectId> {

    private ObjectId user;
    private Double price;
    private LocalDateTime dateTime;
    private ObjectId lot;
    private Status status;
    private ObjectId chat;

}
