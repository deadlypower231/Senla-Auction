package eu.senla.auction.rest.controllers;

import eu.senla.auction.api.dto.CreateUserDto;
import eu.senla.auction.api.dto.UserDto;
import eu.senla.auction.api.repository.UserRepository;
import eu.senla.auction.api.services.IUserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final IUserService userService;

    public AuthController( IUserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public UserDto signup(@RequestBody CreateUserDto createUserDto){
        return userService.saveUser(createUserDto);
    }

}
