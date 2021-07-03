package eu.senla.auction.trading.api.services;

import eu.senla.auction.trading.api.dto.lot.CreateLotDto;
import eu.senla.auction.trading.api.dto.lot.LotDto;
import eu.senla.auction.trading.api.dto.lot.LotIdDto;
import eu.senla.auction.trading.entity.enums.Status;

import java.util.List;

public interface ILotService {

    LotDto addLot(CreateLotDto lotDto);

    List<?> getLotsCurrentUser(Status status);

    List<?> getLotsCurrentUser(Status status, Status paymentStatus);

    List<?> getAllLots(Status status);

    Boolean payment(LotIdDto lotIdDto);

}
