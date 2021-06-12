package eu.senla.auction.service.services;

import eu.senla.auction.api.dto.CreateUserDto;
import eu.senla.auction.api.dto.UserDto;
import eu.senla.auction.api.mappers.UserMapper;
import eu.senla.auction.api.repository.UserRepository;
import eu.senla.auction.api.services.IUserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDto saveUser(CreateUserDto createUserDto) {
        return UserMapper.mapUserDto(userRepository.save(UserMapper.mapCreateUserDto(createUserDto)));
    }

    @Override
    public List<UserDto> findAllUsers() {
        return UserMapper.mapUsersDto(userRepository.findAll());
    }

}
