package ru.mirea.docker.elitetickets.services.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.mirea.docker.elitetickets.dto.models.UserModel;
import ru.mirea.docker.elitetickets.dto.requests.LoginRequest;
import ru.mirea.docker.elitetickets.dto.requests.RegisterRequest;

import javax.security.auth.login.CredentialException;

public interface AuthenticationService {

    UserModel register(RegisterRequest request);

    UserModel login(LoginRequest request, HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws CredentialException;

}
