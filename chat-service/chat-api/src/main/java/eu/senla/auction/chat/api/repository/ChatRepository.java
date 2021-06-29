package eu.senla.auction.chat.api.repository;

import eu.senla.auction.chat.entity.entities.Chat;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChatRepository extends MongoRepository<Chat, ObjectId> {
}
