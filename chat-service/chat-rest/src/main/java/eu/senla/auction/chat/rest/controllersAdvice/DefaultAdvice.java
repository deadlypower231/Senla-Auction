package eu.senla.auction.chat.rest.controllersAdvice;

import eu.senla.auction.chat.api.exceptions.NoAccess;
import eu.senla.auction.chat.api.exceptions.NullPointerExceptionHand;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class DefaultAdvice {

    @ExceptionHandler(NoAccess.class)
    public ResponseEntity<Response> handleExceptions(NoAccess e) {
        return new ResponseEntity<>(new Response(e.getMessage()), HttpStatus.ACCEPTED);
    }

    @ExceptionHandler(NullPointerExceptionHand.class)
    public ResponseEntity<Response> handleExceptionsNull(NullPointerExceptionHand e) {
        return new ResponseEntity<>(new Response(e.getMessage()), HttpStatus.NO_CONTENT);
    }

}
