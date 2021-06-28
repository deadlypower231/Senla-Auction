package eu.senla.auction.trading.api.services;

import eu.senla.auction.trading.api.dto.bet.CreateBetDto;
import org.springframework.validation.BindingResult;

import java.util.Map;

public interface IBetService {

    Boolean addBet(CreateBetDto createBetDto);

}
