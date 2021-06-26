package eu.senla.auction.payment.api.services;

import eu.senla.auction.payment.api.dto.CardDto;

public interface ICardService {

    CardDto addCard(CardDto cardDto);

}
