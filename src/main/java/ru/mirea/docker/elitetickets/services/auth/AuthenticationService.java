package ru.mirea.docker.elitetickets.services.auth;

import ru.mirea.docker.elitetickets.dto.models.UserModel;
import ru.mirea.docker.elitetickets.dto.requests.LoginRequest;
import ru.mirea.docker.elitetickets.dto.requests.RegisterRequest;

public interface AuthenticationService {

    UserModel register(RegisterRequest request);

    UserModel login(LoginRequest request);

}
