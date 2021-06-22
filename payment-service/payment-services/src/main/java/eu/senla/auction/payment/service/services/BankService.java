package eu.senla.auction.payment.service.services;

import eu.senla.auction.payment.api.dto.CreateBankDto;
import eu.senla.auction.payment.api.mappers.BankMapper;
import eu.senla.auction.payment.api.repository.BalanceRepository;
import eu.senla.auction.payment.api.services.IBankService;
import eu.senla.auction.payment.entities.Bank;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@Service
@ComponentScan("eu.senla.auction.payment.api")
public class BankService implements IBankService {

    private final BalanceRepository balanceRepository;

    public BankService(BalanceRepository balanceRepository) {
        this.balanceRepository = balanceRepository;
    }

    @Override
    public CreateBankDto createBank(CreateBankDto createBankDto) {
        Bank bank = new Bank();
        bank.setUserId(createBankDto.getUserId());
        bank.setBalance(createBankDto.getBalance());
        return BankMapper.createMapBank(balanceRepository.save(bank));
    }
}
