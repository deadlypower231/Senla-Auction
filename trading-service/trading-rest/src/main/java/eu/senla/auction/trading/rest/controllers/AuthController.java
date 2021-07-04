package eu.senla.auction.trading.rest.controllers;


import eu.senla.auction.trading.api.dto.user.CreateUserDto;
import eu.senla.auction.trading.api.dto.user.UserDto;
import eu.senla.auction.trading.api.services.IUserService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class AuthController {

    private final IUserService userService;

    public AuthController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public UserDto signup(@RequestBody CreateUserDto createUserDto) {
        return this.userService.saveUser(createUserDto);
    }

    @GetMapping("/success")
    public UserDto getLoginInfo(OAuth2AuthenticationToken authentication) {
        CreateUserDto createUserDto = new CreateUserDto();
        createUserDto.setEmail(authentication.getPrincipal().getAttribute("email"));
        createUserDto.setFirstName(authentication.getPrincipal().getAttribute("given_name"));
        createUserDto.setLastName(authentication.getPrincipal().getAttribute("family_name"));
        createUserDto.setPassword(authentication.getPrincipal().getAttribute("sub"));
        return this.userService.saveUser(createUserDto);
    }

}
