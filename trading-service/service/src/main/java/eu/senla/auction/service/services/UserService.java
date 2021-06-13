package eu.senla.auction.service.services;

import eu.senla.auction.api.dto.CreateUserDto;
import eu.senla.auction.api.dto.UserDto;
import eu.senla.auction.api.mappers.UserMapper;
import eu.senla.auction.api.repository.RoleRepository;
import eu.senla.auction.api.repository.UserRepository;
import eu.senla.auction.api.services.IUserService;
import eu.senla.auction.entity.entities.Role;
import eu.senla.auction.entity.entities.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDto saveUser(CreateUserDto createUserDto) {
        User user = new User();
        Role role = roleRepository.findRoleByRoleName("USER_ROLE");
        user.setFirstName(createUserDto.getFirstName());
        user.setLastName(createUserDto.getLastName());
        user.setRoles(new HashSet<>(Collections.singletonList(role)));
        user.setPassword(passwordEncoder.encode(createUserDto.getPassword()));
        user.setEmail(createUserDto.getEmail());
        user.setBirthday(createUserDto.getBirthday());
        return UserMapper.mapUserDto(userRepository.save(user));
    }

    @Override
    public List<UserDto> findAllUsers() {
        return UserMapper.mapUsersDto(userRepository.findAll());
    }

}
