package eu.senla.auction.payment.api.mappers;

import eu.senla.auction.payment.api.dto.CardDto;
import eu.senla.auction.payment.entities.Card;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CardMapper {

    public Card mapCard(CardDto source) {
        return Card.builder()
                .numberCard(source.getNumberCard())
                .balance(source.getBalance())
                .cvv(source.getCvv())
                .build();
    }

    public CardDto mapCardDto(Card source) {
        return CardDto.builder()
                .numberCard(source.getNumberCard())
                .balance(source.getBalance())
                .cvv(source.getCvv())
                .build();
    }

}
