package eu.senla.auction.trading.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class NotFoundHand extends Exception{

    public NotFoundHand(String message) {
        super(message);
    }

}
