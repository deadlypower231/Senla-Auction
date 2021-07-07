package eu.senla.auction.trading.service.services;

import eu.senla.auction.trading.api.dto.chat.*;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
@ComponentScan(basePackages = {"eu.senla.auction.trading.rest.jwt"})
public class UserService implements IUserService {

    private static final String BANK_SERVICE = "http://payment:8080/bank";
    private static final String CHAT_SERVICE = "http://chat:8080";

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
    public UserDto saveUser(CreateUserDto createUserDto) {
        User newUser = this.userRepository.findByEmail(createUserDto.getEmail());
        if (newUser != null) {
            return UserMapper.mapUserDto(newUser);
        }
        User user = new User();
        Role role = roleRepository.findRoleByRoleName("USER_ROLE");
        user.setFirstName(createUserDto.getFirstName());
        user.setLastName(createUserDto.getLastName());
        user.setRoles(new HashSet<>(Collections.singletonList(role)));
        user.setPassword(passwordEncoder.encode(createUserDto.getPassword()));
        user.setEmail(createUserDto.getEmail());
        user.setBirthday(createUserDto.getBirthday());
        User savedUser = userRepository.save(user);
        log.info("creat new user: {}", savedUser.getId().toString());
        BankDto postBank = new BankDto();
        postBank.setUserId(savedUser.getId().toString());
        postBank.setBalance(0.0);
        BankDto bankDto = restTemplate.postForObject(BANK_SERVICE + "/create", postBank, BankDto.class);
        savedUser.setBalance(Objects.requireNonNull(bankDto).getBalance());
        UserDto result = UserMapper.mapUserDto(this.userRepository.save(savedUser));
        return result;
    }

    @Override
    public HomePageDto getCurrentUser(String token) {
        User user = userRepository.findByEmail(this.securityService.findLoggedInUser());
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity<?> entityReq = new HttpEntity<>("http://localhost:8081/bank" +
                "/getBalanceById{id}", headers);
        ResponseEntity<BankDto> response = restTemplate.exchange("http://localhost:8081/bank" +
                "/getBalanceById{id}", HttpMethod.GET, entityReq, BankDto.class,user.getId());
        user.setBalance(Optional.ofNullable(response.getBody().getBalance()).orElse(null));
        return UserMapper.mapHomePageDto(user);

    }

    @Override
    public Boolean addBalance(BalanceDto balanceDto) {
        User user = userRepository.findByEmail(this.securityService.findLoggedInUser());
        balanceDto.setUserId(String.valueOf(user.getId()));
        Boolean result = restTemplate.postForObject(BANK_SERVICE + "/addBalance", balanceDto, Boolean.class);
        if (Boolean.TRUE.equals(result)) {
            user.setBalance(user.getBalance() + balanceDto.getAmount());
            this.userRepository.save(user);
            return true;
        } else {
            return false;
        }
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
            log.info("Creating new chat id: {}. Dealer - {}, Buyer - {}", response.getBody().getChatId().toString(),
                    response.getBody().getDealerEmail(), response.getBody().getBuyerEmail());
            return chatViewDto;
        }
        return null;
    }

    @Override
    public List<String> getChats() {
        String email = this.securityService.findLoggedInUser();
        ResponseEntity<ChatsDto> response = this.restTemplate.getForEntity(CHAT_SERVICE + "/chat/getChats{email}",
                ChatsDto.class, email);
        if (response.getBody() != null) {
            return response.getBody().getChatsId();
        }
        return new ArrayList<>();
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
