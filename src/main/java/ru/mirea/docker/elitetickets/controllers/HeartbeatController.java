package ru.mirea.docker.elitetickets.controllers;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mirea.docker.elitetickets.dto.models.UserModel;

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
