package eu.senla.auction.payment.api.repository;

import eu.senla.auction.payment.entities.Card;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CardRepository extends MongoRepository<Card, String> {

    Card findByNumberCard(String numberCard);

}
