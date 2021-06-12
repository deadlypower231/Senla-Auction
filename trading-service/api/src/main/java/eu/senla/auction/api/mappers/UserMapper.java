package eu.senla.auction.api.mappers;

import eu.senla.auction.api.dto.CreateUserDto;
import eu.senla.auction.api.dto.UserDto;
import eu.senla.auction.entity.entities.User;
import lombok.experimental.UtilityClass;
import org.springframework.data.mongodb.core.aggregation.DateOperators;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class UserMapper {

    public User mapUser(UserDto source){
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
                .role(source.getRole())
                .build();
    }
//todo date format
    public UserDto mapUserDto(User source){
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
                .role(source.getRole())
                .build();
    }

    public User mapCreateUserDto(CreateUserDto source){
        return User.builder()
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
                .email(source.getEmail())
                .password(source.getPassword())
                .birthday(source.getBirthday())
                .build();
    }

    public List<UserDto> mapUsersDto(List<User> source){
        return source.stream().map(UserMapper::mapUserDto).collect(Collectors.toList());
    }

}
