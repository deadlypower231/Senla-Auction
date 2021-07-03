package eu.senla.auction.payment.service.services;

import eu.senla.auction.payment.api.dto.BalanceDto;
import eu.senla.auction.payment.api.dto.BankDto;
import eu.senla.auction.payment.api.dto.CreateBankDto;
import eu.senla.auction.payment.api.dto.PaymentDto;
import eu.senla.auction.payment.api.mappers.BankMapper;
import eu.senla.auction.payment.api.repository.BalanceRepository;
import eu.senla.auction.payment.api.repository.CardRepository;
import eu.senla.auction.payment.api.repository.TransferRepository;
import eu.senla.auction.payment.api.services.IBankService;
import eu.senla.auction.payment.entities.Bank;
import eu.senla.auction.payment.entities.Card;
import eu.senla.auction.payment.entities.Transfer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@ComponentScan("eu.senla.auction.payment.api")
public class BankService implements IBankService {

    private final BalanceRepository balanceRepository;
    private final CardRepository cardRepository;
    private final TransferRepository transferRepository;

    public BankService(BalanceRepository balanceRepository, CardRepository cardRepository, TransferRepository transferRepository) {
        this.balanceRepository = balanceRepository;
        this.cardRepository = cardRepository;
        this.transferRepository = transferRepository;
    }

    @Override
    public CreateBankDto createBank(CreateBankDto createBankDto) {
        Bank bank = new Bank();
        bank.setUserId(createBankDto.getUserId());
        bank.setBalance(createBankDto.getBalance());
        return BankMapper.createMapBank(balanceRepository.save(bank));
    }

    @Override
    public BankDto getBalanceById(String id) {
        Bank bank = balanceRepository.getBankByUserId(id);
        return BankMapper.mapBankDto(bank);
    }

    @Override
    public Boolean addBalance(BalanceDto balanceDto) {
        Bank bankUser = this.balanceRepository.getBankByUserId(balanceDto.getUserId());
        Card card = this.cardRepository.findByNumberCard(balanceDto.getNumberCard());
        if (bankUser == null ||
                card == null ||
                card.getBalance() < balanceDto.getAmount() ||
                !card.getCvv().equals(balanceDto.getCvv())) {
            return false;
        }
        bankUser.setBalance(bankUser.getBalance() + balanceDto.getAmount());
        card.setBalance(card.getBalance() - balanceDto.getAmount());
        this.balanceRepository.save(bankUser);
        this.cardRepository.save(card);
        return true;
    }

    @Override
    public Boolean payment(PaymentDto paymentDto) {
        Bank buyer = this.balanceRepository.getBankByUserId(paymentDto.getBuyerId());
        Bank dealer = this.balanceRepository.getBankByUserId(paymentDto.getDealerId());
        if (buyer.getBalance() >= paymentDto.getAmount()) {
            Transfer transfer = new Transfer();
            transfer.setBuyerBankId(buyer.getId());
            transfer.setDealerBankId(dealer.getId());
            transfer.setPrice(paymentDto.getAmount());
            this.transferRepository.save(transfer);
            buyer.setBalance(buyer.getBalance() - paymentDto.getAmount());
            dealer.setBalance(dealer.getBalance() + paymentDto.getAmount());
            this.balanceRepository.save(buyer);
            this.balanceRepository.save(dealer);
            return true;
        }else {
            return false;
        }
    }

    @Override
    public List<BankDto> getAll() {
        return BankMapper.mapDtos(this.balanceRepository.findAll());
    }
}
