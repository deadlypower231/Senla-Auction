package eu.senla.auction.trading.api.repository;

import eu.senla.auction.trading.entity.entities.Bet;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BetRepository extends MongoRepository<Bet, ObjectId> {
}