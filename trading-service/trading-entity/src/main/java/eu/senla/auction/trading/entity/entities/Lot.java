package eu.senla.auction.trading.entity.entities;

import eu.senla.auction.trading.entity.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
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
    private Status statusPayment;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Lot lot = (Lot) o;
        return userId.equals(lot.userId) && name.equals(lot.name) && description.equals(lot.description) && dateStart.equals(lot.dateStart) && dateEnd.equals(lot.dateEnd) && price.equals(lot.price) && Objects.equals(userWin, lot.userWin) && Objects.equals(bets, lot.bets) && status == lot.status && Objects.equals(chat, lot.chat) && statusPayment == lot.statusPayment;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), userId, name, description, dateStart, dateEnd, price, userWin, bets, status, chat, statusPayment);
    }
}
