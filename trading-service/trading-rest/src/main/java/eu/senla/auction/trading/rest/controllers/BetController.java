package eu.senla.auction.trading.rest.controllers;

import eu.senla.auction.trading.api.dto.bet.BetDto;
import eu.senla.auction.trading.api.dto.bet.CreateBetDto;
import eu.senla.auction.trading.api.services.IBetService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bet")
public class BetController {

    private final IBetService betService;

    public BetController(IBetService betService) {
        this.betService = betService;
    }

    @PostMapping("/addBet")
    public Boolean addBet(@RequestBody CreateBetDto betDto) {
        return this.betService.addBet(betDto);
    }

}
