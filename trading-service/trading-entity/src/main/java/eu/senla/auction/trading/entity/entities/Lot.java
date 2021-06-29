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
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Document(collection = "lot")
public class Lot extends AEntity<ObjectId> {

    private ObjectId userId;
    private String name;
    private String description;
    private LocalDateTime dateStart;
    private LocalDateTime dateEnd;
    private Double price;
    private ObjectId userWin;
    private List<ObjectId> bets;
    private Status status;
    private ObjectId chat;


}
