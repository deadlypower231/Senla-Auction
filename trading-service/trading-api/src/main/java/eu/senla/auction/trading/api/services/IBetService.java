package eu.senla.auction.trading.api.services;

import eu.senla.auction.trading.api.dto.bet.BetDto;
import eu.senla.auction.trading.api.dto.bet.CreateBetDto;
import eu.senla.auction.trading.entity.enums.Status;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Map;

public interface IBetService {

    Boolean addBet(CreateBetDto createBetDto);

    List<?> getBetsCurrentUser(Status status);

}
