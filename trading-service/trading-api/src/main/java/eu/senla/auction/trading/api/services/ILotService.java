package eu.senla.auction.trading.api.services;

import eu.senla.auction.trading.api.dto.lot.CreateLotDto;
import eu.senla.auction.trading.api.dto.lot.LotDto;
import eu.senla.auction.trading.entity.enums.Status;

import java.util.List;

public interface ILotService {

    LotDto addLot(CreateLotDto lotDto);

    List<LotDto> getLotsCurrentUser(Status status);

    List<LotDto> getAllLots(Status status);

}
