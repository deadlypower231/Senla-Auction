package eu.senla.auction.chat.rest.controllers;

import eu.senla.auction.chat.api.dto.ChatDto;
import eu.senla.auction.chat.api.dto.CreateChatDto;
import eu.senla.auction.chat.api.services.IChatService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chat")
@ComponentScan("eu.senla.auction.chat.service")
public class ChatController {

    private final IChatService chatService;

    public ChatController(IChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping
    public String test() {
        return "test";
    }

    @PostMapping("/createChat")
    public ChatDto createChat(@RequestBody CreateChatDto createChatDto) {
        return this.chatService.createChat(createChatDto);
    }

}
