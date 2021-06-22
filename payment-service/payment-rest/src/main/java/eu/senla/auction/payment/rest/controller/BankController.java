package eu.senla.auction.payment.rest.controller;

import eu.senla.auction.payment.api.dto.CreateBankDto;
import eu.senla.auction.payment.api.services.IBankService;
import eu.senla.auction.payment.entities.Bank;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bank")
@ComponentScan("eu.senla.auction.payment.service")
public class BankController {

    private final IBankService bankService;

    public BankController(IBankService bankService) {
        this.bankService = bankService;
    }

    @PostMapping("/create")
    public CreateBankDto createBank(@RequestBody CreateBankDto createBankDto) {
        return this.bankService.createBank(createBankDto);
    }

}
