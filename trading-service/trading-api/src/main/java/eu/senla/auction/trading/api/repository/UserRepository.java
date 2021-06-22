package eu.senla.auction.trading.api.repository;

import eu.senla.auction.trading.entity.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

    User findByEmail(String email);

}
