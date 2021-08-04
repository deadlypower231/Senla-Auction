package eu.senla.auction.chat.rest.controllers;

import eu.senla.auction.chat.api.dto.ChatDto;
import eu.senla.auction.chat.api.dto.ChatsDto;
import eu.senla.auction.chat.api.dto.CreateChatDto;
import eu.senla.auction.chat.api.services.IChatService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat")
@ComponentScan("eu.senla.auction.chat.service")
public class ChatController {

    private final IChatService chatService;

    public ChatController(IChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/getChats{email}")
    public ResponseEntity<ChatsDto> getChats(@PathVariable String email) {
        return new ResponseEntity<>(this.chatService.getChats(email), HttpStatus.OK);
    }

    @PostMapping("/createChat")
    public ResponseEntity<ChatDto> createChat(@RequestBody CreateChatDto createChatDto) {
        return new ResponseEntity<>(this.chatService.createChat(createChatDto), HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<ChatDto>> getAll(){
        return new ResponseEntity<>(this.chatService.getChats(), HttpStatus.OK);
    }


}
