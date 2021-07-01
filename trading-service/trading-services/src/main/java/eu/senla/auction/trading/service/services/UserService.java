package eu.senla.auction.trading.service.services;

import eu.senla.auction.trading.api.dto.chat.ChatMessageDto;
import eu.senla.auction.trading.api.dto.chat.ChatViewDto;
import eu.senla.auction.trading.api.dto.chat.MessagesDto;
import eu.senla.auction.trading.api.dto.chat.SendMessageDto;
import eu.senla.auction.trading.api.dto.payment.BalanceDto;
import eu.senla.auction.trading.api.dto.payment.BankDto;
import eu.senla.auction.trading.api.dto.user.CreateUserDto;
import eu.senla.auction.trading.api.dto.user.HomePageDto;
import eu.senla.auction.trading.api.dto.user.UserDto;
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

import java.time.LocalDateTime;
import java.util.*;

@Service
@ComponentScan("eu.senla.auction.trading.rest")
public class UserService implements IUserService {

    private static final String BANK_SERVICE = "http://localhost:8081/bank";
    private static final String CHAT_SERVICE = "http://localhost:8082";

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

    //todo rewrite to 1 method
    @Override
    public ChatViewDto sendMessage(SendMessageDto sendMessageDto) {
        sendMessageDto.setEmail(this.securityService.findLoggedInUser());
        ResponseEntity<MessagesDto> response = restTemplate.postForEntity(CHAT_SERVICE + "/message/add",
                sendMessageDto, MessagesDto.class);
        ChatViewDto chatViewDto = new ChatViewDto();
        if (response.getBody() != null) {
            chatViewDto.setId(response.getBody().getChatId().toString());
            chatViewDto.setMessages(buildMessagesForChat(response.getBody()));
        }
        return chatViewDto;
    }

    @Override
    public ChatViewDto chat(ChatMessageDto chatMessageDto) {
        chatMessageDto.setEmail(this.securityService.findLoggedInUser());
        ResponseEntity<MessagesDto> response = restTemplate.postForEntity(CHAT_SERVICE + "/message/chatMessages",
                chatMessageDto, MessagesDto.class);
        ChatViewDto chatViewDto = new ChatViewDto();
        if (response.getBody() != null) {
            chatViewDto.setId(response.getBody().getChatId().toString());
            chatViewDto.setMessages(buildMessagesForChat(response.getBody()));
        }
        return chatViewDto;
    }

    private Map<LocalDateTime, String> buildMessagesForChat(MessagesDto messagesDto) {
        User buyer = this.userRepository.findByEmail(messagesDto.getBuyerEmail());
        User dealer = this.userRepository.findByEmail(messagesDto.getDealerEmail());
        Map<LocalDateTime, String> messages = new TreeMap<>();
        if (messagesDto.getDealerMessage() != null) {
            messagesDto.getDealerMessage().forEach((key, value) -> messages.put(key, dealer.getFirstName() + ": " + value));
        }
        if (messagesDto.getBuyerMessage() != null) {
            messagesDto.getBuyerMessage().forEach((key, value) -> messages.put(key, buyer.getFirstName() + ": " + value));
        }
        return messages;
    }
}
