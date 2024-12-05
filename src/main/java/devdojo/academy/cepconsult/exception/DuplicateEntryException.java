package devdojo.academy.cepconsult.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class DuplicateEntryException extends ResponseStatusException {

    public DuplicateEntryException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
