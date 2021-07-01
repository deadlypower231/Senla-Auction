package eu.senla.auction.chat.rest.controllers;

import eu.senla.auction.chat.api.dto.ChatMessageDto;
import eu.senla.auction.chat.api.dto.MessagesDto;
import eu.senla.auction.chat.api.dto.SendMessageDto;
import eu.senla.auction.chat.api.services.IMessageService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/message")
public class MessageController {

    private final IMessageService messageService;

    public MessageController(IMessageService messageService) {
        this.messageService = messageService;
    }


    @PostMapping("/add")
    public MessagesDto sendMessage(@RequestBody SendMessageDto sendMessageDto) {
        return this.messageService.sendMessage(sendMessageDto);
    }

    @PostMapping("/chatMessages")
    public MessagesDto chat(@RequestBody ChatMessageDto chatMessageDto) {
        return this.messageService.chat(chatMessageDto);
    }

}
