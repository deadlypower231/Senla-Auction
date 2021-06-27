package eu.senla.auction.trading.service.services;

import eu.senla.auction.trading.api.repository.BetRepository;
import eu.senla.auction.trading.api.services.IBetService;
import org.springframework.stereotype.Service;

@Service
public class BetService implements IBetService {

    private final BetRepository betRepository;

    public BetService(BetRepository betRepository) {
        this.betRepository = betRepository;
    }
}
