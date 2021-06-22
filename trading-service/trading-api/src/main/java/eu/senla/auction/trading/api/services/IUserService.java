package eu.senla.auction.trading.api.services;

import eu.senla.auction.trading.api.dto.CreateUserDto;
import eu.senla.auction.trading.api.dto.UserDto;

import java.util.List;

public interface IUserService {

    UserDto saveUser(CreateUserDto createUserDto);

    List<UserDto> findAllUsers();

}
