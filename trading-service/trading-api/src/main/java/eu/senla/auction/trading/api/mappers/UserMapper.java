package eu.senla.auction.trading.api.mappers;

import eu.senla.auction.trading.api.dto.CreateUserDto;
import eu.senla.auction.trading.api.dto.HomePageDto;
import eu.senla.auction.trading.api.dto.UserDto;
import eu.senla.auction.trading.entity.entities.User;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class UserMapper {

    public User mapUser(UserDto source) {
        return User.builder()
                .id(source.getId())
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
                .email(source.getEmail())
                .balance(source.getBalance())
                .password(source.getPassword())
                .birthday(source.getBirthday())
                .lots(source.getLots())
                .bets(source.getBets())
                .roles(source.getRoles())
                .build();
    }

    //todo date format
    public UserDto mapUserDto(User source) {
        return UserDto.builder()
                .id(source.getId())
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
                .email(source.getEmail())
                .balance(source.getBalance())
                .birthday(source.getBirthday())
                .password(source.getPassword())
                .lots(source.getLots())
                .bets(source.getBets())
                .roles(source.getRoles())
                .build();
    }

    public User mapCreateUserDto(CreateUserDto source) {
        return User.builder()
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
                .email(source.getEmail())
                .password(source.getPassword())
                .birthday(source.getBirthday())
                .build();
    }

    public HomePageDto mapHomePageDto(User source) {
        return HomePageDto.builder()
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
                .balance(source.getBalance())
                .birthday(source.getBirthday())
                .email(source.getEmail())
                .roles(source.getRoles())
                .bets(source.getBets())
                .lots(source.getLots())
                .build();
    }

    public List<UserDto> mapUsersDto(List<User> source) {
        return source.stream().map(UserMapper::mapUserDto).collect(Collectors.toList());
    }

}
