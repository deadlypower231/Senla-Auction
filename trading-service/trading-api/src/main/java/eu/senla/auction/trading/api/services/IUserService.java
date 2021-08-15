package eu.senla.auction.trading.api.services;

import eu.senla.auction.trading.api.dto.chat.ChatDto;
import eu.senla.auction.trading.api.dto.chat.ChatMessageDto;
import eu.senla.auction.trading.api.dto.chat.ChatViewDto;
import eu.senla.auction.trading.api.dto.chat.SendMessageDto;
import eu.senla.auction.trading.api.dto.payment.BalanceDto;
import eu.senla.auction.trading.api.dto.payment.BankDto;
import eu.senla.auction.trading.api.dto.user.CreateUserDto;
import eu.senla.auction.trading.api.dto.user.HomePageDto;
import eu.senla.auction.trading.api.dto.user.UserDto;
import eu.senla.auction.trading.api.exceptions.NoAccess;
import eu.senla.auction.trading.api.exceptions.NotFoundHand;
import org.omg.CosNaming.NamingContextPackage.NotFound;

import java.util.List;

public interface IUserService {

    UserDto saveUser(CreateUserDto createUserDto);

    HomePageDto getCurrentUser(String token);

    Boolean addBalance(BalanceDto balanceDto);

    ChatViewDto chat(ChatMessageDto chatMessageDto) throws NoAccess, NotFoundHand;

    ChatViewDto sendMessage(SendMessageDto sendMessageDto);

    List<String> getChats();

}
