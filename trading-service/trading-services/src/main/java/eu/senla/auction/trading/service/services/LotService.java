package eu.senla.auction.trading.service.services;

import eu.senla.auction.trading.api.dto.lot.CreateLotDto;
import eu.senla.auction.trading.api.dto.lot.LotDto;
import eu.senla.auction.trading.api.mappers.LotMapper;
import eu.senla.auction.trading.api.repository.LotRepository;
import eu.senla.auction.trading.api.repository.UserRepository;
import eu.senla.auction.trading.api.services.ILotService;
import eu.senla.auction.trading.api.services.ISecurityService;
import eu.senla.auction.trading.entity.entities.Lot;
import eu.senla.auction.trading.entity.entities.User;
import eu.senla.auction.trading.entity.enums.Status;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class LotService implements ILotService {

    private final ISecurityService securityService;
    private final UserRepository userRepository;
    private final LotRepository lotRepository;

    public LotService(ISecurityService securityService, UserRepository userRepository, LotRepository lotRepository) {
        this.securityService = securityService;
        this.userRepository = userRepository;
        this.lotRepository = lotRepository;
    }

    @Override
    public LotDto addLot(CreateLotDto lotDto) {
        User currentUser = this.userRepository.findByEmail(this.securityService.findLoggedInUser());
        Lot entity = LotMapper.mapCreateLot(lotDto);
        entity.setUserId(currentUser.getId());
        entity.setStatus(Status.INACTIVE);
        entity.setPrice(0.0);
        Lot savedLot = this.lotRepository.save(entity);
        if (currentUser.getLots() == null) {
            currentUser.setLots(Collections.singletonList(savedLot.getId()));
        } else {
            currentUser.getLots().add(savedLot.getId());
        }
        this.userRepository.save(currentUser);
        return LotMapper.mapLotDto(savedLot);
    }

    @Override
    public List<?> getLotsCurrentUser(Status status) {
        User currentUser = this.userRepository.findByEmail(this.securityService.findLoggedInUser());
        List<Lot> result = new ArrayList<>();
        if (currentUser.getLots() != null) {
            Iterable<Lot> lots = this.lotRepository.findAllById(currentUser.getLots());
            for (Lot x : lots) {
                if (x.getStatus().equals(status)) {
                    result.add(x);
                }
            }
        }
        if (status.equals(Status.ACTIVE)) {
            return LotMapper.mapLotsActiveDto(result);
        } else if (status.equals(Status.INACTIVE)) {
            return LotMapper.mapLotsInactiveDto(result);
        } else {
            return LotMapper.mapLotsCompletedCurrentUserDto(result);
        }
    }

    @Override
    public List<?> getAllLots(Status status) {
        Iterable<Lot> lots = this.lotRepository.findAllByStatus(status);
        List<Lot> result = new ArrayList<>();
        lots.forEach(result::add);
        if (status.equals(Status.INACTIVE)) {
            return LotMapper.mapLotsInactiveDto(result);
        } else if (status.equals(Status.COMPLETED)) {
            return LotMapper.mapLotsCompletedDto(result);
        } else {
            return LotMapper.mapLotsActiveDto(result);
        }
    }

}
