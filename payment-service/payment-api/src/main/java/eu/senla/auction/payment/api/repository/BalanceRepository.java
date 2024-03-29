package eu.senla.auction.payment.api.repository;

import eu.senla.auction.payment.entities.Bank;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface BalanceRepository extends MongoRepository<Bank, String> {

    Bank getBankByUserId(String userId);
    Optional<Bank> findByUserId(String id);

}
