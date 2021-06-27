package eu.senla.auction.trading.api.repository;

import eu.senla.auction.trading.entity.entities.Lot;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LotRepository extends MongoRepository<Lot, ObjectId> {
}
