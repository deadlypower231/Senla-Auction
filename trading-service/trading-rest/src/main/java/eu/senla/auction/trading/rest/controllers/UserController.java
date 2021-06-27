package eu.senla.auction.trading.rest.controllers;

import eu.senla.auction.trading.api.dto.BalanceDto;
import eu.senla.auction.trading.api.dto.CreateUserDto;
import eu.senla.auction.trading.api.dto.HomePageDto;
import eu.senla.auction.trading.api.dto.UserDto;
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

    public UserController(IUserService userService, ILotService lotService) {
        this.userService = userService;
        this.lotService = lotService;
    }

    //test method
    @GetMapping("/getAll")
    public List<UserDto> getAll() {
        return userService.findAllUsers();
    }

    //todo
    @GetMapping
    public Map<String, Object> homePage() {
        Map<String, Object> result = new HashMap<>();
        result.put("currentUser", this.userService.getCurrentUser());
        result.put("activeLots", this.lotService.getLots(Status.ACTIVE));
        result.put("inactiveLots", this.lotService.getLots(Status.INACTIVE));
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
