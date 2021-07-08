package eu.senla.auction.payment.service.services;

import eu.senla.auction.payment.api.dto.CardDto;
import eu.senla.auction.payment.api.mappers.CardMapper;
import eu.senla.auction.payment.api.repository.CardRepository;
import eu.senla.auction.payment.api.services.ICardService;
import eu.senla.auction.payment.entities.Card;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CardService implements ICardService {

    private final CardRepository cardRepository;

    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Override
    public CardDto addCard(CardDto cardDto) throws DuplicateKeyException {
        try {
            Card entity = CardMapper.mapCard(cardDto);
            this.cardRepository.save(entity);
            return CardMapper.mapCardDto(entity);
        }catch (DuplicateKeyException e){
            throw new DuplicateKeyException("This card exist");
        }
    }
}
