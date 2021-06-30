package eu.senla.auction.trading.rest.controllers;

import eu.senla.auction.trading.api.dto.payment.BalanceDto;
import eu.senla.auction.trading.api.dto.user.CreateUserDto;
import eu.senla.auction.trading.api.dto.user.HomePageDto;
import eu.senla.auction.trading.api.dto.user.UserDto;
import eu.senla.auction.trading.api.services.IBetService;
import eu.senla.auction.trading.api.services.ILotService;
import eu.senla.auction.trading.api.services.IUserService;
import eu.senla.auction.trading.entity.enums.Status;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    //test method
    @GetMapping("/getAll")
    public List<UserDto> getAll() {
        return userService.findAllUsers();
    }

    @GetMapping
    public Map<String, Object> homePage() {
        Map<String, Object> result = new HashMap<>();
        result.put("currentUser", this.userService.getCurrentUser());
        result.put("activeLots", this.lotService.getAllLots(Status.ACTIVE));
        result.put("futureLots", this.lotService.getAllLots(Status.INACTIVE));
        result.put("completedLots", this.lotService.getAllLots(Status.COMPLETED));
        return result;
    }

    @GetMapping("/personalCabinet")
    public Map<String, Object> personalCabinet(){
        Map<String, Object> result = new HashMap<>();
        result.put("currentUser", this.userService.getCurrentUser());
        result.put("activeLots", this.lotService.getLotsCurrentUser(Status.ACTIVE));
        result.put("inactiveLots", this.lotService.getLotsCurrentUser(Status.INACTIVE));
        result.put("completedLots", this.lotService.getLotsCurrentUser(Status.COMPLETED));
        result.put("activeBets", this.betService.getBetsCurrentUser(Status.ACTIVE));
        result.put("inactiveBets", this.betService.getBetsCurrentUser(Status.INACTIVE));
        result.put("winBets", this.betService.getBetsCurrentUser(Status.WINNER));
        return result;
    }

    @PostMapping("/addBalance")
    public Boolean addBalance(@RequestBody BalanceDto balanceDto) {
        return this.userService.addBalance(balanceDto);
    }

    @PatchMapping("/update")
    public HomePageDto updUser(@RequestBody CreateUserDto userDto) {
        return this.userService.updUser(userDto);
    }

}
