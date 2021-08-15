package eu.senla.auction.chat.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class NoAccess extends Exception {

    public NoAccess(String message) {
        super(message);
    }

}