package eu.senla.auction.trading.service.services;

import eu.senla.auction.trading.api.dto.chat.ChatDto;
import eu.senla.auction.trading.api.dto.chat.CreateChatDto;
import eu.senla.auction.trading.api.dto.lot.CreateLotDto;
import eu.senla.auction.trading.api.dto.lot.LotDto;
import eu.senla.auction.trading.api.dto.lot.LotIdDto;
import eu.senla.auction.trading.api.dto.payment.PaymentDto;
import eu.senla.auction.trading.api.mappers.LotMapper;
import eu.senla.auction.trading.api.repository.BetRepository;
import eu.senla.auction.trading.api.repository.LotRepository;
import eu.senla.auction.trading.api.repository.UserRepository;
import eu.senla.auction.trading.api.services.ILotService;
import eu.senla.auction.trading.api.services.ISecurityService;
import eu.senla.auction.trading.api.services.IUserService;
import eu.senla.auction.trading.entity.entities.Bet;
import eu.senla.auction.trading.entity.entities.Lot;
import eu.senla.auction.trading.entity.entities.User;
import eu.senla.auction.trading.entity.enums.Status;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class LotService implements ILotService {

    private static final String CHAT_SERVICE = "http://chat:8080/chat";
    private static final String PAYMENT_SERVICE = "http://payment:8080/bank";

    private final RestTemplate restTemplate;
    private final BetRepository betRepository;
    private final ISecurityService securityService;
    private final UserRepository userRepository;
    private final LotRepository lotRepository;

    public LotService(RestTemplate restTemplate, BetRepository betRepository, ISecurityService securityService,
                      UserRepository userRepository, LotRepository lotRepository) {
        this.restTemplate = restTemplate;
        this.betRepository = betRepository;
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
        entity.setStatusPayment(Status.UNPAID);
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
        return checkPaid(status, result);
    }

    @Override
    public List<?> getLotsCurrentUser(Status status,Status paymentStatus) {
        User currentUser = this.userRepository.findByEmail(this.securityService.findLoggedInUser());
        List<Lot> result = new ArrayList<>();
        if (currentUser.getLots() != null) {
            Iterable<Lot> lots = this.lotRepository.findAllById(currentUser.getLots());
            for (Lot x : lots) {
                if (x.getStatus().equals(status) && x.getStatusPayment().equals(paymentStatus)) {
                    result.add(x);
                }
            }
        }
        return checkPaid(status, result);
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

    @Override
    public Boolean payment(LotIdDto lotIdDto) {
        User user = this.userRepository.findByEmail(this.securityService.findLoggedInUser());
        Lot lot = this.lotRepository.findById(lotIdDto.getLotId());
        if (user.getId().toString().equals(lot.getUserWin().toString()) &&
                user.getBalance() >= lot.getPrice() &&
                lot.getStatusPayment().equals(Status.UNPAID)) {
            PaymentDto payment = new PaymentDto();
            payment.setAmount(lot.getPrice());
            payment.setBuyerId(user.getId().toString());
            payment.setDealerId(lot.getUserId().toString());
            Boolean result = this.restTemplate.postForObject(PAYMENT_SERVICE + "/payment", payment, Boolean.class);
            if (Boolean.TRUE.equals(result)) {
                user.setBalance(user.getBalance() - payment.getAmount());
                User dealer = this.userRepository.findById(lot.getUserId());
                dealer.setBalance(dealer.getBalance() + payment.getAmount());
                this.userRepository.save(user);
                this.userRepository.save(dealer);
                List<Bet> bets = (List<Bet>) this.betRepository.findAllById(lot.getBets());
                Optional<Bet> optionalBet = bets.stream().max(Comparator.comparing(Bet::getPrice));
                Bet winBet = optionalBet.get();
                ChatDto chatDto = createChatDto(lot);
                if (chatDto != null) {
                    lot.setChat(chatDto.getId());
                    lot.setStatusPayment(Status.PAID);
                    winBet.setChat(chatDto.getId());
                    winBet.setPaymentStatus(Status.PAID);
                    this.lotRepository.save(lot);
                    this.betRepository.save(winBet);
                    return true;
                }
            }
        }
        return false;
    }

    private ChatDto createChatDto(Lot lot) {
        CreateChatDto createChatDto = new CreateChatDto();
        createChatDto.setLotId(lot.getId().toString());
        createChatDto.setDealerEmail(this.userRepository.findById(lot.getUserId()).getEmail());
        createChatDto.setBuyerEmail(this.userRepository.findById(lot.getUserWin()).getEmail());
        ResponseEntity<ChatDto> responseEntity = restTemplate.postForEntity(CHAT_SERVICE + "/createChat",
                createChatDto, ChatDto.class);
        return responseEntity.getBody();
    }

    private List<?> checkPaid(Status status, List<Lot> result){
        if (status.equals(Status.ACTIVE)) {
            return LotMapper.mapLotsActiveDto(result);
        } else if (status.equals(Status.INACTIVE)) {
            return LotMapper.mapLotsInactiveDto(result);
        } else {
            return LotMapper.mapLotsCompletedCurrentUserDto(result);
        }
    }


}

