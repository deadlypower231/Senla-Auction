package eu.senla.auction.trading.rest.ControllersAdvice;

import eu.senla.auction.trading.api.exceptions.NoAccess;
import eu.senla.auction.trading.api.exceptions.NotFoundHand;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class DefaultAdvice {

    @ExceptionHandler(NoAccess.class)
    public ResponseEntity<Response> handleExceptions(NoAccess e) {
        return new ResponseEntity<>(new Response(e.getMessage()), HttpStatus.OK);
    }

    @ExceptionHandler(NotFoundHand.class)
    public ResponseEntity<Response> handleExceptions(NotFoundHand e) {
        return new ResponseEntity<>(new Response(e.getMessage()), HttpStatus.OK);
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<Response> handleExceptions(DuplicateKeyException e) {
        return new ResponseEntity<>(new Response(e.getMessage()), HttpStatus.OK);
    }

}