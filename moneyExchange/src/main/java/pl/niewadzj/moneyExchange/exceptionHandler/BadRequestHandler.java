package pl.niewadzj.moneyExchange.exceptionHandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.niewadzj.moneyExchange.exceptions.BadRequestException;

@Slf4j
@RestControllerAdvice
public class BadRequestHandler {

    @ExceptionHandler({BadRequestException.class})
    public final ResponseEntity<String> badRequestHandler(BadRequestException exception){
        String message = exception.getMessage();

        log.error("There has been an error trying to find an entity in repository");

        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }
}
