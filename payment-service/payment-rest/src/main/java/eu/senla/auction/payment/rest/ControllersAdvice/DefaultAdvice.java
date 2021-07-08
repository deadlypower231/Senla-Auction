package eu.senla.auction.payment.rest.ControllersAdvice;

import eu.senla.auction.payment.rest.exceptions.DuplicateException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class DefaultAdvice {

    @ExceptionHandler(DuplicateException.class)
    public ResponseEntity<Response> handleExceptions(DuplicateException e) {
        return new ResponseEntity<>(new Response(e.getMessage()), HttpStatus.OK);
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<Response> handleExceptionsdke(DuplicateKeyException e) {
        return new ResponseEntity<>(new Response(e.getMessage()), HttpStatus.OK);
    }


}
