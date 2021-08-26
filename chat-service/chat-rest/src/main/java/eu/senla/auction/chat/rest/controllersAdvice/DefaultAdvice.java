package eu.senla.auction.chat.rest.controllersAdvice;

import eu.senla.auction.chat.api.exceptions.NoAccessException;
import eu.senla.auction.chat.api.exceptions.NullPointerHandException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class DefaultAdvice {

    @ExceptionHandler(NoAccessException.class)
    public ResponseEntity<Response> handleExceptions(NoAccessException e) {
        return new ResponseEntity<>(new Response(e.getMessage()), HttpStatus.ACCEPTED);
    }

    @ExceptionHandler(NullPointerHandException.class)
    public ResponseEntity<Response> handleExceptionsNull(NullPointerHandException e) {
        return new ResponseEntity<>(new Response(e.getMessage()), HttpStatus.NO_CONTENT);
    }

}
