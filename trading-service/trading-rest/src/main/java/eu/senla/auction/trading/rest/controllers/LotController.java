package eu.senla.auction.trading.rest.controllers;

import eu.senla.auction.trading.api.dto.lot.CreateLotDto;
import eu.senla.auction.trading.api.dto.lot.LotDto;
import eu.senla.auction.trading.api.dto.lot.LotIdDto;
import eu.senla.auction.trading.api.services.ILotService;
import eu.senla.auction.trading.api.utils.IScheduledTask;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lot")
public class LotController {

    private final ILotService lotService;

    public LotController(ILotService lotService) {
        this.lotService = lotService;
    }

    @PostMapping("/addLot")
    public LotDto addLot(@RequestBody CreateLotDto lotDto) {
        return this.lotService.addLot(lotDto);
    }

    @PostMapping("/payment")
    public Boolean payment(@RequestBody LotIdDto lotIdDto){
        return this.lotService.payment(lotIdDto);
    }


}
