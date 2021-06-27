package eu.senla.auction.trading.service.services;

import eu.senla.auction.trading.api.dto.*;
import eu.senla.auction.trading.api.mappers.UserMapper;
import eu.senla.auction.trading.api.repository.RoleRepository;
import eu.senla.auction.trading.api.repository.UserRepository;
import eu.senla.auction.trading.api.services.ISecurityService;
import eu.senla.auction.trading.api.services.IUserService;
import eu.senla.auction.trading.entity.entities.Role;
import eu.senla.auction.trading.entity.entities.User;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@ComponentScan("eu.senla.auction.trading.rest")
public class UserService implements IUserService {

    private static final String BANK_SERVICE = "http://localhost:8081/bank";

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final RestTemplate restTemplate;
    private final ISecurityService securityService;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, RestTemplate restTemplate, ISecurityService securityService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.restTemplate = restTemplate;
        this.securityService = securityService;
    }

    //test
    @Override
    public HomePageDto updUser(CreateUserDto createUserDto) {
        User user = this.userRepository.findByEmail(this.securityService.findLoggedInUser());
        user.setLastName(createUserDto.getLastName());

        return UserMapper.mapHomePageDto(this.userRepository.save(user));
    }

    @Override
    @Transactional
    public UserDto saveUser(CreateUserDto createUserDto) {
        User user = new User();
        Role role = roleRepository.findRoleByRoleName("USER_ROLE");
        user.setFirstName(createUserDto.getFirstName());
        user.setLastName(createUserDto.getLastName());
        user.setRoles(new HashSet<>(Collections.singletonList(role)));
        user.setPassword(passwordEncoder.encode(createUserDto.getPassword()));
        user.setEmail(createUserDto.getEmail());
        user.setBirthday(createUserDto.getBirthday());
        UserDto savedUser = UserMapper.mapUserDto(userRepository.save(user));

        BankDto postBank = new BankDto();
        postBank.setUserId(savedUser.getId());
        postBank.setBalance(0.0);
        BankDto bankDto = restTemplate.postForObject(BANK_SERVICE + "/create", postBank, BankDto.class);
        savedUser.setBalance(Optional.ofNullable(bankDto.getBalance()).orElse(0.0));

        return savedUser;
    }

    @Override
    @Transactional
    public HomePageDto getCurrentUser() {
        User user = userRepository.findByEmail(this.securityService.findLoggedInUser());
        ResponseEntity<BankDto> response = restTemplate.getForEntity(BANK_SERVICE + "/getBalanceById{id}", BankDto.class, user.getId());
        user.setBalance(Optional.ofNullable(response.getBody().getBalance()).orElse(null));
        return UserMapper.mapHomePageDto(user);
    }

    @Override
    public Boolean addBalance(BalanceDto balanceDto) {
        User user = userRepository.findByEmail(this.securityService.findLoggedInUser());
        balanceDto.setUserId(String.valueOf(user.getId()));
        return restTemplate.postForObject(BANK_SERVICE + "/addBalance", balanceDto, Boolean.class);
    }

    @Override
    public List<UserDto> findAllUsers() {
        return UserMapper.mapUsersDto(userRepository.findAll());
    }


}
