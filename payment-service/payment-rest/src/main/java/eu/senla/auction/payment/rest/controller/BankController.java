package eu.senla.auction.payment.rest.controller;

import eu.senla.auction.payment.api.dto.BalanceDto;
import eu.senla.auction.payment.api.dto.BankDto;
import eu.senla.auction.payment.api.dto.CreateBankDto;
import eu.senla.auction.payment.api.dto.PaymentDto;
import eu.senla.auction.payment.api.services.IBankService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bank")
@Slf4j
@ComponentScan("eu.senla.auction.payment.service")
public class BankController {

    private final IBankService bankService;

    public BankController(IBankService bankService) {
        this.bankService = bankService;
    }

    @PostMapping("/create")
    public ResponseEntity<CreateBankDto> createBank(@RequestBody CreateBankDto createBankDto) throws DuplicateKeyException {
        try {
            return new ResponseEntity<>(this.bankService.createBank(createBankDto), HttpStatus.OK);
        }catch (DuplicateKeyException e){
            log.info("Bank with id: {} exists", createBankDto.getUserId());
            throw new DuplicateKeyException("A bank with this id exists!");
        }
    }

    @GetMapping("/getAll")
    public List<BankDto> getAllBank(){
        return this.bankService.getAll();
    }

    @GetMapping("/getBalanceById/{id}")
    public ResponseEntity<BankDto> getBalanceById(@PathVariable("id") String id) {
        return new ResponseEntity<>(this.bankService.getBalanceById(id), HttpStatus.OK);
    }

    @PostMapping("/addBalance")
    public Boolean addBalance(@RequestBody BalanceDto balanceDto) {
        return this.bankService.addBalance(balanceDto);
    }

    @PostMapping("/payment")
    public Boolean lotPayment(@RequestBody PaymentDto paymentDto) {
        return this.bankService.payment(paymentDto);
    }

}
