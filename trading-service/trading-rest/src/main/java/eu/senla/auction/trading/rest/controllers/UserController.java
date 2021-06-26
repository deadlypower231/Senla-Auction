package eu.senla.auction.trading.rest.controllers;

import eu.senla.auction.trading.api.dto.BalanceDto;
import eu.senla.auction.trading.api.dto.HomePageDto;
import eu.senla.auction.trading.api.dto.UserDto;
import eu.senla.auction.trading.api.services.IUserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    //test method
    @GetMapping("/getAll")
    public List<UserDto> getAll() {
        return userService.findAllUsers();
    }

    @GetMapping
    public HomePageDto homePage() {
        return userService.getCurrentUser();
    }

    @PostMapping("/addBalance")
    public Boolean addBalance(@RequestBody BalanceDto balanceDto) {
        return this.userService.addBalance(balanceDto);
    }

}
