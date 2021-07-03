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
        return (source != null) ? UserDto.builder()
                .id(String.valueOf(source.getId()))
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
                .email(source.getEmail())
                .balance(source.getBalance())
                .birthday(source.getBirthday())
                .build() : null;
    }

    public HomePageDto mapHomePageDto(User source) {
        return HomePageDto.builder()
                .id(source.getId().toString())
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
                .balance(source.getBalance())
                .birthday(source.getBirthday())
                .email(source.getEmail())
                .build();
    }

    public User mapUser(HomePageDto source) {
        return (source != null) ? User.builder()
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
                .email(source.getEmail())
                .birthday(source.getBirthday())
                .balance(source.getBalance())
                .build() : null;
    }

    public List<UserDto> mapUsersDto(List<User> source) {
        return source.stream().map(UserMapper::mapUserDto).collect(Collectors.toList());
    }

}
