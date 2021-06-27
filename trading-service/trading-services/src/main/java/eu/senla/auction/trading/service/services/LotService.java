package eu.senla.auction.trading.service.services;

import eu.senla.auction.trading.api.dto.CreateLotDto;
import eu.senla.auction.trading.api.dto.LotDto;
import eu.senla.auction.trading.api.mappers.LotMapper;
import eu.senla.auction.trading.api.repository.LotRepository;
import eu.senla.auction.trading.api.repository.UserRepository;
import eu.senla.auction.trading.api.services.ILotService;
import eu.senla.auction.trading.api.services.ISecurityService;
import eu.senla.auction.trading.entity.entities.Lot;
import eu.senla.auction.trading.entity.entities.User;
import eu.senla.auction.trading.entity.enums.Status;
import org.springframework.stereotype.Service;

import java.util.Collections;

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
        entity.setStatus(Status.INACTIVE);
        entity.setPrice(0.0);
        Lot savedLot = this.lotRepository.save(entity);
        if (currentUser.getLots() == null){
            currentUser.setLots(Collections.singletonList(savedLot.getId().toString()));
        }else {
            currentUser.getLots().add(savedLot.getId().toString());
        }
        this.userRepository.save(currentUser);
        return LotMapper.mapLotDto(savedLot);
    }
}
