package eu.senla.auction.payment.rest.controller;

import eu.senla.auction.payment.api.dto.CardDto;
import eu.senla.auction.payment.api.services.ICardService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/card")
public class CardController {

    private final ICardService cardService;

    public CardController(ICardService cardService) {
        this.cardService = cardService;
    }

    @PostMapping("/add")
    public CardDto addCard(@RequestBody CardDto cardDto) {
        return this.cardService.addCard(cardDto);
    }

}
