package eu.senla.auction.trading.api.repository;

import eu.senla.auction.trading.entity.entities.Bet;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BetRepository extends MongoRepository<Bet, ObjectId> {

    List<Bet> findAllById(List<String> ids);

}
