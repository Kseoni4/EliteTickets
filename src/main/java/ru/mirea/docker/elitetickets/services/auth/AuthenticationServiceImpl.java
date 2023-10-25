package ru.mirea.docker.elitetickets.services.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mirea.docker.elitetickets.dao.UserDao;
import ru.mirea.docker.elitetickets.dto.models.UserModel;
import ru.mirea.docker.elitetickets.dto.requests.LoginRequest;
import ru.mirea.docker.elitetickets.dto.requests.RegisterRequest;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService{

    private final UserDao userDao;

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
    public UserModel login(LoginRequest request) {
        return null;
    }
}
