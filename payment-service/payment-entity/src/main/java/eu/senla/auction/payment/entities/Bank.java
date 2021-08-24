package eu.senla.auction.payment.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.bson.types.ObjectId;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Document(collection = "bank")
public class Bank extends AEntity<ObjectId> {

    @Indexed(unique = true)
    private String userId;
    private Double balance;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Bank bank = (Bank) o;
        return userId.equals(bank.userId) && balance.equals(bank.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), userId, balance);
    }
}
