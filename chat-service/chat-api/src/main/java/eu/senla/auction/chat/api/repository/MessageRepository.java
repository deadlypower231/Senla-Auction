package eu.senla.auction.chat.api.repository;

import eu.senla.auction.chat.entity.entities.Message;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.util.ObjectUtils;

import java.util.List;

public interface MessageRepository extends MongoRepository<Message, ObjectId> {

}
