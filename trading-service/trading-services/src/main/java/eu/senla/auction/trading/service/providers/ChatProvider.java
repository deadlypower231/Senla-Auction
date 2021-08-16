package eu.senla.auction.trading.service.providers;

import eu.senla.auction.trading.api.dto.chat.ChatMessageDto;
import eu.senla.auction.trading.api.dto.chat.ChatsDto;
import eu.senla.auction.trading.api.dto.chat.MessagesDto;
import eu.senla.auction.trading.api.dto.chat.SendMessageDto;
import eu.senla.auction.trading.api.providers.IChatProviders;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@PropertySource("classpath:application.properties")
public class ChatProvider implements IChatProviders {

    private final RestTemplate restTemplate;
    private final Environment env;

    private final String CHAT_URI = "spring.provider.chat";

    public ChatProvider(RestTemplate restTemplate, Environment env) {
        this.restTemplate = restTemplate;
        this.env = env;
    }

    @Override
    public ResponseEntity<MessagesDto> getChat(ChatMessageDto chatMessageDto){
        return restTemplate.postForEntity(env.getProperty(CHAT_URI) + "/message/chatMessages",
                chatMessageDto, MessagesDto.class);
    }

    @Override
    public ResponseEntity<ChatsDto> getChats(String email){
        return restTemplate.getForEntity(env.getProperty(CHAT_URI) + "/chat/getChats{email}",
                ChatsDto.class, email);
    }

    @Override
    public ResponseEntity<MessagesDto> sendMessages(SendMessageDto sendMessageDto){
        return restTemplate.postForEntity(env.getProperty(CHAT_URI) + "/message/add",
                sendMessageDto, MessagesDto.class);
    }

}
