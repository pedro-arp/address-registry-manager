package devdojo.academy.cepconsult.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorHandlerAdvice {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<DefaultErrorMessage> handleNotFoundException(NotFoundException e) {

        var defaultErrorMessage = new DefaultErrorMessage(HttpStatus.NOT_FOUND.value(), e.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(defaultErrorMessage);

    }

    @ExceptionHandler(DuplicateEntryException.class)
    public ResponseEntity<DefaultErrorMessage> handleNotFoundException(DuplicateEntryException e) {

        var defaultErrorMessage = new DefaultErrorMessage(HttpStatus.BAD_REQUEST.value(), e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(defaultErrorMessage);

    }

}
