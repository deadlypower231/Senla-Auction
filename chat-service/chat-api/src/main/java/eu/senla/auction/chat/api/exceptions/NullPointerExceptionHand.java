package eu.senla.auction.chat.api.exceptions;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class NullPointerExceptionHand extends Exception {

    public NullPointerExceptionHand(String message) {
        super(message);
    }

}