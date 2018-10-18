package exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class MethodFailureException extends RuntimeException {

    public MethodFailureException(String message) {
        super(message);
    }

}