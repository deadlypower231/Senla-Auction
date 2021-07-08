package eu.senla.auction.payment.api.services;

import com.mongodb.DuplicateKeyException;
import eu.senla.auction.payment.api.dto.CardDto;

public interface ICardService {

    CardDto addCard(CardDto cardDto) throws DuplicateKeyException;

}
