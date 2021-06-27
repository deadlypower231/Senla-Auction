package eu.senla.auction.trading.rest.controllers;

import eu.senla.auction.trading.api.dto.bet.BetDto;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bet")
public class BetController {

    @PostMapping("/addBet")
    public BindingResult addBet(@RequestBody BetDto betDto) {
        return null;
    }

}
