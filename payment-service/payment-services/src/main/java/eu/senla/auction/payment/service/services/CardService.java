package eu.senla.auction.payment.service.services;

import eu.senla.auction.payment.api.dto.CardDto;
import eu.senla.auction.payment.api.mappers.CardMapper;
import eu.senla.auction.payment.api.repository.CardRepository;
import eu.senla.auction.payment.api.services.ICardService;
import eu.senla.auction.payment.entities.Card;
import org.springframework.stereotype.Service;

@Service
public class CardService implements ICardService {

    private final CardRepository cardRepository;

    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Override
    public CardDto addCard(CardDto cardDto) {
        Card entity = CardMapper.mapCard(cardDto);
        return CardMapper.mapCardDto(this.cardRepository.save(entity));
    }
}
