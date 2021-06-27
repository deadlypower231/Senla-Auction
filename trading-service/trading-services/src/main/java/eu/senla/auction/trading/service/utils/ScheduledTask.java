package eu.senla.auction.trading.service.utils;

import eu.senla.auction.trading.api.repository.LotRepository;
import eu.senla.auction.trading.api.utils.IScheduledTask;
import eu.senla.auction.trading.entity.entities.Lot;
import eu.senla.auction.trading.entity.enums.Status;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ScheduledTask implements IScheduledTask {

    private final LotRepository lotRepository;

    public ScheduledTask(LotRepository lotRepository) {
        this.lotRepository = lotRepository;
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
            x.setStatus(Status.FINISHED);
        }
        this.lotRepository.saveAll(lots);


    }

}
