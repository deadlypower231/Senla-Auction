package eu.senla.auction.payment.rest.controller;

import eu.senla.auction.payment.api.dto.CardDto;
import eu.senla.auction.payment.api.services.ICardService;
import eu.senla.auction.payment.rest.exceptions.DuplicateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
@RequestMapping("/card")
public class CardController {

    private final ICardService cardService;

    public CardController(ICardService cardService) {
        this.cardService = cardService;
    }

    @PostMapping("/add")
    public ResponseEntity<CardDto> addCard(@RequestBody CardDto cardDto) throws DuplicateException {
        try {
            return new ResponseEntity<>(this.cardService.addCard(cardDto), HttpStatus.OK);
        } catch (DuplicateKeyException e) {
            log.info("This card exist: {}", cardDto.getNumberCard());
            throw new DuplicateException("This card is exist!");
        }
    }

}
