package eu.senla.auction.trading.service.services;

import eu.senla.auction.trading.api.dto.chat.*;
import eu.senla.auction.trading.api.dto.payment.BalanceDto;
import eu.senla.auction.trading.api.dto.payment.BankDto;
import eu.senla.auction.trading.api.dto.user.CreateUserDto;
import eu.senla.auction.trading.api.dto.user.HomePageDto;
import eu.senla.auction.trading.api.dto.user.UserDto;
import eu.senla.auction.trading.api.exceptions.NoAccess;
import eu.senla.auction.trading.api.exceptions.NotFoundHand;
import eu.senla.auction.trading.api.mappers.UserMapper;
import eu.senla.auction.trading.api.providers.IChatProviders;
import eu.senla.auction.trading.api.providers.IPaymentProviders;
import eu.senla.auction.trading.api.repository.RoleRepository;
import eu.senla.auction.trading.api.repository.UserRepository;
import eu.senla.auction.trading.api.services.ISecurityService;
import eu.senla.auction.trading.api.services.IUserService;
import eu.senla.auction.trading.entity.entities.Role;
import eu.senla.auction.trading.entity.entities.User;
import eu.senla.auction.trading.service.asyncServices.AsyncUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final RestTemplate restTemplate;
    private final ISecurityService securityService;
    private final IChatProviders chatProviders;
    private final IPaymentProviders paymentProviders;
    private final AsyncUserService asyncUserService;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, RestTemplate restTemplate, ISecurityService securityService, IChatProviders chatProviders, IPaymentProviders paymentProviders, AsyncUserService asyncUserService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.restTemplate = restTemplate;
        this.securityService = securityService;
        this.chatProviders = chatProviders;
        this.paymentProviders = paymentProviders;
        this.asyncUserService = asyncUserService;
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
        try {
            BankDto bankDto = paymentProviders.createBalance(postBank);
            savedUser.setBalance(Objects.requireNonNull(bankDto).getBalance());
        } catch (Exception e) {
            log.info("Access denied to the bank server! user: {}", savedUser.getEmail());
            savedUser.setBalance(0.0);
            asyncUserService.createBalance(postBank);
        }
        return UserMapper.mapUserDto(this.userRepository.save(savedUser));
    }


    @Override
    public HomePageDto getCurrentUser(String token) {
        User user = userRepository.findByEmail(this.securityService.findLoggedInUser());
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity<?> entityReq = new HttpEntity<>("http://localhost:8081/bank" +
                "/getBalanceById/{id}", headers);
        ResponseEntity<BankDto> response = restTemplate.exchange("http://localhost:8081/bank" +
                "/getBalanceById/{id}", HttpMethod.GET, entityReq, BankDto.class, user.getId());
        user.setBalance(Optional.ofNullable(response.getBody().getBalance()).orElse(null));
        return UserMapper.mapHomePageDto(user);

    }

    @Override
    public Boolean addBalance(BalanceDto balanceDto) {
        User user = userRepository.findByEmail(this.securityService.findLoggedInUser());
        balanceDto.setUserId(String.valueOf(user.getId()));
        Boolean result = paymentProviders.addBalance(balanceDto);
        if (Boolean.TRUE.equals(result)) {
            user.setBalance(user.getBalance() + balanceDto.getAmount());
            this.userRepository.save(user);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public ChatViewDto sendMessage(SendMessageDto sendMessageDto) {
        sendMessageDto.setEmail(this.securityService.findLoggedInUser());
        ResponseEntity<MessagesDto> response = chatProviders.sendMessages(sendMessageDto);
        ChatViewDto chatViewDto = new ChatViewDto();
        if (response.getBody() != null) {
            chatViewDto.setId(response.getBody().getChatId().toString());
            chatViewDto.setMessages(buildMessagesForChat(response.getBody()));
        }
        return chatViewDto;
    }

    @Override
    public ChatViewDto chat(ChatMessageDto chatMessageDto) throws NoAccess, NotFoundHand {
        chatMessageDto.setEmail(this.securityService.findLoggedInUser());
        ResponseEntity<MessagesDto> response = chatProviders.getChat(chatMessageDto);
        ChatViewDto chatViewDto = new ChatViewDto();
        if (response.getStatusCodeValue() == 202) {
            throw new NoAccess("You don't have access!");
        } else if (response.getStatusCodeValue() == 204) {
            throw new NotFoundHand("Does not exist!");
        } else {
            chatViewDto.setId(Objects.requireNonNull(response.getBody()).getChatId().toString());
            chatViewDto.setMessages(buildMessagesForChat(response.getBody()));
            return chatViewDto;
        }
    }

    @Override
    public List<String> getChats() {
        String email = this.securityService.findLoggedInUser();
        ResponseEntity<ChatsDto> response = chatProviders.getChats(email);
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
