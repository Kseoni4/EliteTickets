package ru.mirea.docker.elitetickets.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Энд-поинт для проверки активности сервиса.
 */
@RestController
public class HeartbeatController {

    @GetMapping("/ping")
    public String healthcheck(){
        return "ping";
    }

}
