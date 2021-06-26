package eu.senla.auction.payment.rest.controller;

import eu.senla.auction.payment.api.dto.BalanceDto;
import eu.senla.auction.payment.api.dto.BankDto;
import eu.senla.auction.payment.api.dto.CreateBankDto;
import eu.senla.auction.payment.api.services.IBankService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/getBalanceById{id}")
    public BankDto getBalanceById(@PathVariable String id) {
        return this.bankService.getBalanceById(id);
    }

    @PostMapping("/addBalance")
    public Boolean addBalance(@RequestBody BalanceDto balanceDto) {
        return this.bankService.addBalance(balanceDto);
    }

    @GetMapping("/getAll")
    public List<BankDto> getAll() {
        return this.bankService.getAll();
    }

}
