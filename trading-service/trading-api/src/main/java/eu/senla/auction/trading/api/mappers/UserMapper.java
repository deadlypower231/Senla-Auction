package eu.senla.auction.trading.api.mappers;

import eu.senla.auction.trading.api.dto.bet.BetDto;
import eu.senla.auction.trading.api.dto.lot.LotDto;
import eu.senla.auction.trading.api.dto.user.CreateUserDto;
import eu.senla.auction.trading.api.dto.user.HomePageDto;
import eu.senla.auction.trading.api.dto.user.UserDto;
import eu.senla.auction.trading.entity.entities.Bet;
import eu.senla.auction.trading.entity.entities.Lot;
import eu.senla.auction.trading.entity.entities.User;
import lombok.experimental.UtilityClass;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class UserMapper {

    public UserDto mapUserDto(User source) {
        if (source == null){
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

    public User mapUser(UserDto source) {
        return User.builder()
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
                .email(source.getEmail())
                .balance(source.getBalance())
                .birthday(source.getBirthday())
                .password(source.getPassword())
//                .lots(source.getLots().stream().map(UserMapper::mapLot).collect(Collectors.toList()))
//                .bets(source.getBets().stream().map(UserMapper::mapBet).collect(Collectors.toList()))
//                .roles(source.getRoles().stream().map(UserMapper::mapRole).collect(Collectors.toSet()))
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
                .build();
    }

    public List<UserDto> mapUsersDto(List<User> source) {
        return source.stream().map(UserMapper::mapUserDto).collect(Collectors.toList());
    }


    private BetDto mapBetDto(Bet source) {
        if (source == null){
            return null;
        }
        return BetDto.builder()
                .price(source.getPrice())
                .status(source.getStatus())
                .build();
    }

    private LotDto mapLotDto(Lot source) {
        if (source == null){
            return null;
        }
        return LotDto.builder()
                .userWin(UserMapper.mapUserDto(source.getUserWin()))
//                .bets(source.getBets().stream().map(UserMapper::mapBetDto).collect(Collectors.toList()))
                .dateEnd(source.getDateEnd().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .dateStart(source.getDateStart().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .description(source.getDescription())
                .name(source.getName())
                .price(source.getPrice())
                .build();
    }

}
