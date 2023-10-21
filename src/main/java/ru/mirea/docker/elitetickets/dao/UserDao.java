package ru.mirea.docker.elitetickets.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.mirea.docker.elitetickets.dto.models.UserModel;
import ru.mirea.docker.elitetickets.entities.UserEntity;
import ru.mirea.docker.elitetickets.repositories.UserRepository;

@Component
@RequiredArgsConstructor
public class UserDao {

    private final UserRepository userRepository;
    public UserEntity getUserByEmail(String email){
        return userRepository.findByEmail(email).orElseThrow();
    }
}
