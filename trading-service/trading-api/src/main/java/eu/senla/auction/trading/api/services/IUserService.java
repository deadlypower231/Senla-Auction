package eu.senla.auction.trading.api.services;

import eu.senla.auction.trading.api.dto.*;

import java.util.List;

public interface IUserService {

    UserDto saveUser(CreateUserDto createUserDto);

    List<UserDto> findAllUsers();

    HomePageDto getCurrentUser();

    Boolean addBalance(BalanceDto balanceDto);
    //test
    HomePageDto updUser(CreateUserDto createUserDto);

}
