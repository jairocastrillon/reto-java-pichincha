package co.com.bancopichincha.retojava.exception;

import co.com.bancopichincha.retojava.adapters.primary.rest.response.RestMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {NotFoundException.class})
    protected ResponseEntity<RestMessage> handleNotFoundException(NotFoundException ex) {
        RestMessage restMessage = new RestMessage(new Date(), ex.getMessage());
        return new ResponseEntity<>(restMessage, HttpStatus.NOT_FOUND);
    }
}
