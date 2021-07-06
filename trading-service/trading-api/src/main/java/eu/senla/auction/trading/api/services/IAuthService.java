package eu.senla.auction.trading.api.services;

import eu.senla.auction.trading.api.dto.user.UserDto;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

public interface IAuthService {

    UserDto createOrLoginUer(OAuth2AuthenticationToken authentication);

}
