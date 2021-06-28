package eu.senla.auction.trading.api.mappers;

import eu.senla.auction.trading.api.dto.user.HomePageDto;
import eu.senla.auction.trading.api.dto.user.UserDto;
import eu.senla.auction.trading.entity.entities.User;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class UserMapper {

    public UserDto mapUserDto(User source) {
        if (source == null) {
            return null;
        }
        return UserDto.builder()
                .id(String.valueOf(source.getId()))
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
                .email(source.getEmail())
                .balance(source.getBalance())
                .birthday(source.getBirthday())
                .password(source.getPassword())
                .build();
    }

    public HomePageDto mapHomePageDto(User source) {
        return HomePageDto.builder()
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
                .balance(source.getBalance())
                .birthday(source.getBirthday())
                .email(source.getEmail())
                .build();
    }

    public List<UserDto> mapUsersDto(List<User> source) {
        return source.stream().map(UserMapper::mapUserDto).collect(Collectors.toList());
    }

}
