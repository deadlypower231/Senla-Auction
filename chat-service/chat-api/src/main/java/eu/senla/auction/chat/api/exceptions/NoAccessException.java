package eu.senla.auction.chat.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class NoAccessException extends Exception {

    public NoAccessException(String message) {
        super(message);
    }

}