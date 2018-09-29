package exceptions.httpExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.METHOD_FAILURE)
public class MethodFailureException extends RuntimeException {}