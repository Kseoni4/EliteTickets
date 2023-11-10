package ru.mirea.docker.elitetickets.services.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;
import ru.mirea.docker.elitetickets.dao.UserDao;
import ru.mirea.docker.elitetickets.dto.models.UserModel;
import ru.mirea.docker.elitetickets.dto.requests.LoginRequest;
import ru.mirea.docker.elitetickets.dto.requests.RegisterRequest;

import javax.security.auth.login.CredentialException;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserDao userDao;

    private final SecurityContextRepository securityContextRepository;

    @Override
    public UserModel register(RegisterRequest request) {
       if(userDao.getUserByEmail(request.getEmail()) != null){
           throw new RuntimeException("This email is already taken");
       }

        UserModel newUser = UserModel.builder()
                        .email(request.getEmail())
                        .firstName(request.getFirstName())
                        .lastName(request.getLastName())
                        .birthDate(request.getBirthDate())
                        .build();

       return userDao.registerUser(newUser, request.getPassword());
    }

    @Override
    public UserModel login(LoginRequest request, HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws CredentialException {
        if(!userDao.validateUser(request.getEmail(), request.getPassword())){
            throw new CredentialException("Wrong username or password");
        }

        UserModel user = userDao.getUserByEmail(request.getEmail());

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                user.getUsername(), user.getPassword(), user.getAuthorities()
        );

        SecurityContext context = SecurityContextHolder.createEmptyContext();

        context.setAuthentication(token);

        SecurityContextHolder.setContext(context);

        securityContextRepository.saveContext(context, servletRequest, servletResponse);

        return user;
    }
}
