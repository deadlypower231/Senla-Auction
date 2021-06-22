package eu.senla.auction.payment.api.repository;

import eu.senla.auction.payment.entities.Transfer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TransferRepository extends MongoRepository<Transfer, String> {
}
