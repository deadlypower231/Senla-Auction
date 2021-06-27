package eu.senla.auction.trading.api.repository;

import eu.senla.auction.trading.entity.entities.Lot;
import eu.senla.auction.trading.entity.enums.Status;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface LotRepository extends MongoRepository<Lot, ObjectId> {

    List<Lot> findAllByStatus(Status status);

}
