package eu.senla.auction.trading.service.utils;

import eu.senla.auction.trading.api.repository.BetRepository;
import eu.senla.auction.trading.api.repository.LotRepository;
import eu.senla.auction.trading.api.repository.UserRepository;
import eu.senla.auction.trading.api.utils.IScheduledTask;
import eu.senla.auction.trading.entity.entities.Bet;
import eu.senla.auction.trading.entity.entities.Lot;
import eu.senla.auction.trading.entity.enums.Status;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ScheduledTask implements IScheduledTask {

    private final LotRepository lotRepository;
    private final BetRepository betRepository;
    private final UserRepository userRepository;

    public ScheduledTask(LotRepository lotRepository, BetRepository betRepository, UserRepository userRepository) {
        this.lotRepository = lotRepository;
        this.betRepository = betRepository;
        this.userRepository = userRepository;
    }

    //    @Scheduled(cron = "0 * * * * *")
    @Scheduled(fixedRate = 10000)
    @Override
    public void checkStartLots() {
        List<Lot> lots = this.lotRepository.findAllByStatus(Status.INACTIVE).stream()
                .filter(x -> x.getDateStart().isBefore(LocalDateTime.now())).collect(Collectors.toList());
        for (Lot x : lots) {
            x.setStatus(Status.ACTIVE);
        }
        this.lotRepository.saveAll(lots);
    }

    @Scheduled(fixedRate = 10000)
    @Override
    public void checkEndLots() {
        List<Lot> lots = this.lotRepository.findAllByStatus(Status.ACTIVE).stream()
                .filter(x -> x.getDateEnd().isBefore(LocalDateTime.now())).collect(Collectors.toList());
        for (Lot x : lots) {
            x.setStatus(Status.COMPLETED);
            if (x.getBets() != null) {
                List<Bet> bets = (List<Bet>) this.betRepository.findAllById(x.getBets());
                Optional<Bet> optionalBet = bets.stream().max((i, j) -> i.getPrice().compareTo(j.getPrice()));
                Bet winBet = new Bet();
                if (optionalBet.isPresent()) {
                    winBet = optionalBet.get();
                }
                x.setUserWin(winBet.getUser());
                winBet.setStatus(Status.WINNER);
                this.betRepository.save(winBet);
            }
        }
        this.lotRepository.saveAll(lots);


    }

}
