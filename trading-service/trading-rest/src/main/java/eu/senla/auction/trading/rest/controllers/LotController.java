package eu.senla.auction.trading.rest.controllers;

import eu.senla.auction.trading.api.dto.CreateLotDto;
import eu.senla.auction.trading.api.dto.LotDto;
import eu.senla.auction.trading.api.services.ILotService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lot")
public class LotController {

    private final ILotService lotService;

    public LotController(ILotService lotService) {
        this.lotService = lotService;
    }

    @PostMapping("/addLot")
    public LotDto addLot(@RequestBody CreateLotDto lotDto){
        return this.lotService.addLot(lotDto);
    }

}
