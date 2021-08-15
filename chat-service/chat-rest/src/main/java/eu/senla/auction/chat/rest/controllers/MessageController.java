package eu.senla.auction.chat.rest.controllers;

import eu.senla.auction.chat.api.dto.ChatMessageDto;
import eu.senla.auction.chat.api.dto.MessagesDto;
import eu.senla.auction.chat.api.dto.SendMessageDto;
import eu.senla.auction.chat.api.exceptions.NoAccess;
import eu.senla.auction.chat.api.exceptions.NullPointerExceptionHand;
import eu.senla.auction.chat.api.services.IMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/message")
public class MessageController {

    private final IMessageService messageService;

    public MessageController(IMessageService messageService) {
        this.messageService = messageService;
    }


    @PostMapping("/add")
    public ResponseEntity<MessagesDto> sendMessage(@RequestBody SendMessageDto sendMessageDto) {
        return new ResponseEntity<>(this.messageService.sendMessage(sendMessageDto), HttpStatus.OK);
    }

    @PostMapping("/chatMessages")
    public ResponseEntity<MessagesDto> chat(@RequestBody ChatMessageDto chatMessageDto) throws NoAccess, NullPointerExceptionHand{
        try {
            return new ResponseEntity<>(this.messageService.chat(chatMessageDto), HttpStatus.OK);
        } catch (NoAccess e) {
            log.info("This user: {}, tried to access the chat: {}", chatMessageDto.getEmail(), chatMessageDto.getChatId());
            throw new NoAccess("You don't have access!");
        } catch (NullPointerExceptionHand e){
            log.info("This user: {}, tried to access the chat: {}, does not exist",
                    chatMessageDto.getEmail(), chatMessageDto.getChatId());
            throw new NullPointerExceptionHand("Does not exist!");
        }
    }

}
