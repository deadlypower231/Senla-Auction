package eu.senla.auction.chat.entity.entities;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Objects;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Document(collection = "chat")
public class Chat extends AEntity<ObjectId> {

    private String lotId;
    private String dealerEmail;
    private String buyerEmail;
    private List<ObjectId> dealerMessages;
    private List<ObjectId> buyerMessages;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Chat chat = (Chat) o;
        return lotId.equals(chat.lotId) && dealerEmail.equals(chat.dealerEmail) && buyerEmail.equals(chat.buyerEmail) && Objects.equals(dealerMessages, chat.dealerMessages) && Objects.equals(buyerMessages, chat.buyerMessages);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), lotId, dealerEmail, buyerEmail, dealerMessages, buyerMessages);
    }
}
