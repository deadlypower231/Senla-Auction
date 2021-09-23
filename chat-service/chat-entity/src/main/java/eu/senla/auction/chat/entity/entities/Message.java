package eu.senla.auction.chat.entity.entities;

import eu.senla.auction.chat.entity.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Document(collection = "message")
public class Message extends AEntity<ObjectId> {

    private String text;
    private Date timePublication;
    private Status status;
    private Status notificationStatus;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Message message = (Message) o;
        return text.equals(message.text) && timePublication.equals(message.timePublication) && status == message.status && notificationStatus == message.notificationStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), text, timePublication, status, notificationStatus);
    }
}
