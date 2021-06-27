package eu.senla.auction.trading.api.services;

import eu.senla.auction.trading.api.dto.CreateLotDto;
import eu.senla.auction.trading.api.dto.LotDto;

public interface ILotService {

    LotDto addLot(CreateLotDto lotDto);

}
