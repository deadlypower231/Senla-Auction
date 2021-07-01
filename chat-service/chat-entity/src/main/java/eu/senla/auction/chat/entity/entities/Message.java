package eu.senla.auction.chat.entity.entities;

import eu.senla.auction.chat.entity.enums.Status;
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
@Document(collection = "message")
public class Message extends AEntity<ObjectId> {

    private String text;
    private LocalDateTime timePublication;
    private Status status;
    private Status notificationStatus;

}
