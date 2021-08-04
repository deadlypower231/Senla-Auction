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
import java.util.Objects;

@Setter
@Getter
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
    private Status paymentStatus;
    private ObjectId chat;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Bet bet = (Bet) o;
        return user.equals(bet.user) && Objects.equals(price, bet.price) && dateTime.equals(bet.dateTime) && lot.equals(bet.lot) && status == bet.status && paymentStatus == bet.paymentStatus && Objects.equals(chat, bet.chat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), user, price, dateTime, lot, status, paymentStatus, chat);
    }
}
