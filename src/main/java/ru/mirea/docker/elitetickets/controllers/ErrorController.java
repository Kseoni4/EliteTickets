package ru.mirea.docker.elitetickets.controllers;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.mirea.docker.elitetickets.dto.response.ErrorResponse;

import javax.security.auth.login.CredentialException;

/**
 * Контроллер для перехвата исключений<br>
 * Позволяет корректно обрабатывать 5ХХ ошибки и передавать на фронт-энд ошибку как объект ответа.
 */
@Slf4j
@ControllerAdvice
public class ErrorController {

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<Object> handleExpiredJwtException(ExpiredJwtException e){
        return handleExceptionInternal(new ErrorResponse(e.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(CredentialException.class)
    public ResponseEntity<Object> handleWrongCredentialException(CredentialException e){
        return handleExceptionInternal(new ErrorResponse(e.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    private ResponseEntity<Object> handleExceptionInternal(ErrorResponse body, HttpStatus code){
        return ResponseEntity.status(code).contentType(MediaType.APPLICATION_JSON).body(body);
    }
}
