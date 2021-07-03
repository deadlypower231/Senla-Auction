package eu.senla.auction.trading.rest.controllers;

import eu.senla.auction.trading.api.dto.chat.ChatMessageDto;
import eu.senla.auction.trading.api.dto.chat.ChatViewDto;
import eu.senla.auction.trading.api.dto.chat.SendMessageDto;
import eu.senla.auction.trading.api.dto.payment.BalanceDto;
import eu.senla.auction.trading.api.services.IBetService;
import eu.senla.auction.trading.api.services.ILotService;
import eu.senla.auction.trading.api.services.IUserService;
import eu.senla.auction.trading.entity.enums.Status;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

@RestController
@RequestMapping("/users")
public class UserController {

    private final IUserService userService;
    private final ILotService lotService;
    private final IBetService betService;

    public UserController(IUserService userService, ILotService lotService, IBetService betService) {
        this.userService = userService;
        this.lotService = lotService;
        this.betService = betService;
    }

    @GetMapping
    public Map<String, Object> homePage() {
        Map<String, Object> result = new TreeMap<>();
        result.put("currentUser", this.userService.getCurrentUser());
        result.put("activeLots", this.lotService.getAllLots(Status.ACTIVE));
        result.put("futureLots", this.lotService.getAllLots(Status.INACTIVE));
        result.put("completedLots", this.lotService.getAllLots(Status.COMPLETED));
        return result;
    }

    @GetMapping("/personalCabinet")
    public Map<String, Object> personalCabinet() {
        Map<String, Object> result = new TreeMap<>();
        result.put("currentUser", this.userService.getCurrentUser());
        result.put("activeLots", this.lotService.getLotsCurrentUser(Status.ACTIVE));
        result.put("inactiveLots", this.lotService.getLotsCurrentUser(Status.INACTIVE));
        result.put("completedLots", this.lotService.getLotsCurrentUser(Status.COMPLETED, Status.UNPAID));
        result.put("completedPaidLots", this.lotService.getLotsCurrentUser(Status.COMPLETED, Status.PAID));
        result.put("activeBets", this.betService.getBetsCurrentUser(Status.ACTIVE));
        result.put("inactiveBets", this.betService.getBetsCurrentUser(Status.INACTIVE));
        result.put("winBets", this.betService.getBetsCurrentUser(Status.WINNER, Status.UNPAID));
        result.put("winPaidBets", this.betService.getBetsCurrentUser(Status.WINNER, Status.PAID));
        result.put("chats", this.userService.getChats());
        return result;
    }

    @PostMapping("/addBalance")
    public Boolean addBalance(@RequestBody BalanceDto balanceDto) {
        return this.userService.addBalance(balanceDto);
    }

    @GetMapping("/chat")
    public ChatViewDto chat(@RequestBody ChatMessageDto chatMessageDto) {
        return this.userService.chat(chatMessageDto);
    }

    @PostMapping("/sendMessage")
    public ChatViewDto sendMessage(@RequestBody SendMessageDto sendMessageDto) {
        return this.userService.sendMessage(sendMessageDto);
    }
}
