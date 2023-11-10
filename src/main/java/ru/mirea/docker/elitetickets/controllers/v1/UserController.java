package ru.mirea.docker.elitetickets.controllers.v1;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mirea.docker.elitetickets.dto.models.UserModel;
import ru.mirea.docker.elitetickets.dto.requests.LoginRequest;
import ru.mirea.docker.elitetickets.dto.requests.RegisterRequest;
import ru.mirea.docker.elitetickets.services.auth.AuthenticationService;

import javax.security.auth.login.CredentialException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/auth")
public class UserController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public UserModel register(@RequestBody RegisterRequest request){
        return authenticationService.register(request);
    }

    @PostMapping("/login")
    public UserModel login(@RequestBody LoginRequest request, HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws CredentialException {
        return authenticationService.login(request, servletRequest, servletResponse);
    }
}
