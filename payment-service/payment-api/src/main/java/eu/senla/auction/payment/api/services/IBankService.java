package eu.senla.auction.payment.api.services;

import eu.senla.auction.payment.api.dto.BalanceDto;
import eu.senla.auction.payment.api.dto.BankDto;
import eu.senla.auction.payment.api.dto.CreateBankDto;
import eu.senla.auction.payment.api.dto.PaymentDto;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.List;

public interface IBankService {

    CreateBankDto createBank(CreateBankDto createBankDto);

    BankDto getBalanceById(String id);

    List<BankDto> getAll();

    Boolean addBalance(BalanceDto balanceDto);

    Boolean payment(PaymentDto paymentDto);

}
