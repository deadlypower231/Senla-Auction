package eu.senla.auction.payment.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "transfer")
public class Transfer extends AEntity<String> {

    private ObjectId lotId;
    private ObjectId dealerBankId;
    private ObjectId buyerBankId;
    private Double price;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Transfer transfer = (Transfer) o;
        return lotId.equals(transfer.lotId) && dealerBankId.equals(transfer.dealerBankId) && buyerBankId.equals(transfer.buyerBankId) && price.equals(transfer.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), lotId, dealerBankId, buyerBankId, price);
    }
}
