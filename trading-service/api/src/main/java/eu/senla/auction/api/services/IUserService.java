package eu.senla.auction.api.services;

import eu.senla.auction.api.dto.CreateUserDto;
import eu.senla.auction.api.dto.UserDto;

import java.util.List;

public interface IUserService {

    UserDto saveUser(CreateUserDto createUserDto);

    List<UserDto> findAllUsers();

}
