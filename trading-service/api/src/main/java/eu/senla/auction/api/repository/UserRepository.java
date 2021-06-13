package eu.senla.auction.api.repository;

import eu.senla.auction.entity.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

    User findByEmail(String email);

}
