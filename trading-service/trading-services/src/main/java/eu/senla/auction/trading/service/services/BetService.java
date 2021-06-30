package eu.senla.auction.trading.service.services;

import eu.senla.auction.trading.api.dto.bet.BetDto;
import eu.senla.auction.trading.api.dto.bet.CreateBetDto;
import eu.senla.auction.trading.api.mappers.BetMapper;
import eu.senla.auction.trading.api.repository.BetRepository;
import eu.senla.auction.trading.api.repository.LotRepository;
import eu.senla.auction.trading.api.repository.UserRepository;
import eu.senla.auction.trading.api.services.IBetService;
import eu.senla.auction.trading.api.services.ISecurityService;
import eu.senla.auction.trading.entity.entities.Bet;
import eu.senla.auction.trading.entity.entities.Lot;
import eu.senla.auction.trading.entity.entities.User;
import eu.senla.auction.trading.entity.enums.Status;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class BetService implements IBetService {

    private final BetRepository betRepository;
    private final LotRepository lotRepository;
    private final ISecurityService securityService;
    private final UserRepository userRepository;

    public BetService(BetRepository betRepository, LotRepository lotRepository, ISecurityService securityService, UserRepository userRepository) {
        this.betRepository = betRepository;
        this.lotRepository = lotRepository;
        this.securityService = securityService;
        this.userRepository = userRepository;
    }

    @Override
    public Boolean addBet(CreateBetDto createBetDto) {
        User currentUser = this.userRepository.findByEmail(this.securityService.findLoggedInUser());
        Optional<Lot> lot = this.lotRepository.findById(createBetDto.getLot());
        Lot lot1 = lot.get();
        if (lot1.getPrice() >= createBetDto.getPrice()){
            return false;
        }
        if (lot1.getBets() != null) {
            Iterable<Bet> bets = this.betRepository.findAllById(lot1.getBets());
            bets.forEach(x -> x.setStatus(Status.INACTIVE));
            this.betRepository.saveAll(bets);
        }
        Bet bet = BetMapper.mapCreateBet(createBetDto);
        bet.setUser(currentUser.getId());
        bet.setDateTime(LocalDateTime.now());
        bet.setStatus(Status.ACTIVE);
        Bet savedBet = this.betRepository.save(bet);
        if (currentUser.getBets() == null){
            currentUser.setBets(Collections.singletonList(savedBet.getId()));
        }else {
            currentUser.getBets().add(savedBet.getId());
        }
        this.userRepository.save(currentUser);
        lot1.setPrice(createBetDto.getPrice());
        if (lot1.getBets() == null){
            lot1.setBets(Collections.singletonList(savedBet.getId()));
        }else {
            lot1.getBets().add(savedBet.getId());
        }
        this.lotRepository.save(lot1);
            return true;
    }

    @Override
    public List<BetDto> getBetsCurrentUser(Status status) {
        User currentUser = this.userRepository.findByEmail(this.securityService.findLoggedInUser());
        List<Bet> result = new ArrayList<>();
        if (currentUser.getBets() != null) {
            Iterable<Bet> bets = this.betRepository.findAllById(currentUser.getBets());
            for (Bet x : bets) {
                if (x.getStatus().equals(status)) {
                    result.add(x);
                }
            }
        }
        return BetMapper.mapBetsDto(result);
    }
}
