package eu.senla.auction.rest.controllers;

import eu.senla.auction.api.dto.CreateUserDto;
import eu.senla.auction.api.dto.UserDto;
import eu.senla.auction.api.services.IUserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    //todo test method
    @GetMapping
    public List<UserDto> say() {
        return userService.findAllUsers();
    }

    @PostMapping("/create")
    public UserDto createUser(@RequestBody CreateUserDto createUserDto) {
        return userService.saveUser(createUserDto);
    }

}
