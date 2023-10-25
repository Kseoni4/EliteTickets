package ru.mirea.docker.elitetickets.controllers.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mirea.docker.elitetickets.dto.models.UserModel;
import ru.mirea.docker.elitetickets.dto.requests.RegisterRequest;
import ru.mirea.docker.elitetickets.services.auth.AuthenticationService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/auth")
public class UserController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public UserModel register(@RequestBody RegisterRequest request){
        return authenticationService.register(request);
    }

}
