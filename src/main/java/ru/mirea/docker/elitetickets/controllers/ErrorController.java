package ru.mirea.docker.elitetickets.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Контроллер для перехвата сообщений об ошибке<br>
 * Позволяет корректно обрабатывать 5ХХ ошибки на фронт-энде.
 */
@ControllerAdvice
public class ErrorController extends ResponseEntityExceptionHandler {

}
