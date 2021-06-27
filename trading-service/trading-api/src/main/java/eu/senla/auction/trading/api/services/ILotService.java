package eu.senla.auction.trading.api.services;

import eu.senla.auction.trading.api.dto.CreateLotDto;
import eu.senla.auction.trading.api.dto.LotDto;
import eu.senla.auction.trading.entity.enums.Status;

import java.util.List;

public interface ILotService {

    LotDto addLot(CreateLotDto lotDto);

    List<LotDto> getLots(Status status);

}
