package eu.senla.auction.trading.service.utils;

import eu.senla.auction.trading.api.dto.chat.ChatDto;
import eu.senla.auction.trading.api.dto.chat.CreateChatDto;
import eu.senla.auction.trading.api.repository.BetRepository;
import eu.senla.auction.trading.api.repository.LotRepository;
import eu.senla.auction.trading.api.repository.UserRepository;
import eu.senla.auction.trading.api.utils.IScheduledTask;
import eu.senla.auction.trading.entity.entities.Bet;
import eu.senla.auction.trading.entity.entities.Lot;
import eu.senla.auction.trading.entity.enums.Status;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@EnableScheduling
public class ScheduledTask implements IScheduledTask {

    private static String CHAT_SERVICE = "http://localhost:8082/chat";

    private final LotRepository lotRepository;
    private final BetRepository betRepository;
    private final UserRepository userRepository;
    private final RestTemplate restTemplate;

    public ScheduledTask(LotRepository lotRepository, BetRepository betRepository, UserRepository userRepository, RestTemplate restTemplate) {
        this.lotRepository = lotRepository;
        this.betRepository = betRepository;
        this.userRepository = userRepository;
        this.restTemplate = restTemplate;
    }

    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(50);
        return taskScheduler;
    }

    @Scheduled(cron = "0 0 * * * *")
    @Override
    public void checkStartLots() {
        List<Lot> lots = this.lotRepository.findAllByStatus(Status.INACTIVE).stream()
                .filter(x -> x.getDateStart().isBefore(LocalDateTime.now())).collect(Collectors.toList());
        for (Lot x : lots) {
            x.setStatus(Status.ACTIVE);
        }
        this.lotRepository.saveAll(lots);
    }

    @Scheduled(cron = "0 0 * * * *")
    @Override
    public void checkEndLots() {
        List<Lot> lots = this.lotRepository.findAllByStatus(Status.ACTIVE).stream()
                .filter(x -> x.getDateEnd().isBefore(LocalDateTime.now())).collect(Collectors.toList());
        for (Lot x : lots) {
            x.setStatus(Status.COMPLETED);
            if (x.getBets() != null) {
                List<Bet> bets = (List<Bet>) this.betRepository.findAllById(x.getBets());
                Optional<Bet> optionalBet = bets.stream().max((i, j) -> i.getPrice().compareTo(j.getPrice()));
                Bet winBet = new Bet();
                if (optionalBet.isPresent()) {
                    winBet = optionalBet.get();
                }
                x.setUserWin(winBet.getUser());
                winBet.setStatus(Status.WINNER);
                ChatDto chatDto = createChatDto(x);
                if (chatDto != null) {
                    x.setChat(chatDto.getId());
                    winBet.setChat(chatDto.getId());
                }
                this.betRepository.save(winBet);
            }

        }
        this.lotRepository.saveAll(lots);
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

}
