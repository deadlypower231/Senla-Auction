package eu.senla.auction.payment.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Document(collection = "card")
public class Card extends AEntity<ObjectId> {

    @Indexed(unique = true)
    private String numberCard;
    private Double balance;
    private String cvv;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Card card = (Card) o;
        return numberCard.equals(card.numberCard) && balance.equals(card.balance) && cvv.equals(card.cvv);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), numberCard, balance, cvv);
    }
}
