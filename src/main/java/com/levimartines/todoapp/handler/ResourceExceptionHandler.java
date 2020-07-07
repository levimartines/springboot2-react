package com.levimartines.todoapp.handler;


import com.levimartines.todoapp.exceptions.AuthorizationException;
import com.levimartines.todoapp.exceptions.InvalidRequestException;
import com.levimartines.todoapp.exceptions.ObjectNotFoundException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ResourceExceptionHandler {


    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e,
        HttpServletRequest request) {
        StandardError err = new StandardError(System.currentTimeMillis(),
            HttpStatus.NOT_FOUND.value(), "Not found",
            e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public final ResponseEntity<StandardError> handleAccessDeniedException(AccessDeniedException e,
        HttpServletRequest request) {
        StandardError err = new StandardError(System.currentTimeMillis(),
            HttpStatus.FORBIDDEN.value(), "Forbidden",
            e.getMessage(), request.getRequestURI());
        return new ResponseEntity<>(err, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<StandardError> validation(InvalidRequestException e,
        HttpServletRequest request) {
        StandardError err = new StandardError(System.currentTimeMillis(),
            HttpStatus.UNPROCESSABLE_ENTITY.value(), "Validation error",
            e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(err);
    }

    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<StandardError> notAuthorized(AuthorizationException e,
        HttpServletRequest request) {
        StandardError err = new StandardError(System.currentTimeMillis(),
            HttpStatus.FORBIDDEN.value(), "Forbidden",
            e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(err);
    }

}
