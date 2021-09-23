package eu.senla.auction.chat.api.repository;

import eu.senla.auction.chat.entity.entities.Chat;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Set;

public interface ChatRepository extends MongoRepository<Chat, ObjectId> {

    Chat findById(String id);

    List<Chat> findAllByBuyerEmail(String email);

    List<Chat> findAllByDealerEmail(String email);


}
