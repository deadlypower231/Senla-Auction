package eu.senla.auction.trading.service.services;

import eu.senla.auction.trading.api.dto.user.CreateUserDto;
import eu.senla.auction.trading.api.dto.user.UserDto;
import eu.senla.auction.trading.api.services.IAuthService;
import eu.senla.auction.trading.api.services.IUserService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements IAuthService {

    private final IUserService userService;

    public AuthService(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDto createOrLoginUer(OAuth2AuthenticationToken authentication) {
        CreateUserDto createUserDto = new CreateUserDto();
        createUserDto.setEmail(authentication.getPrincipal().getAttribute("email"));
        createUserDto.setFirstName(authentication.getPrincipal().getAttribute("given_name"));
        createUserDto.setLastName(authentication.getPrincipal().getAttribute("family_name"));
        createUserDto.setPassword(authentication.getPrincipal().getAttribute("sub"));
        return this.userService.saveUser(createUserDto);
    }
}
