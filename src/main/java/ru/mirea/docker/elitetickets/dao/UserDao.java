package ru.mirea.docker.elitetickets.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.mirea.docker.elitetickets.dto.models.UserModel;
import ru.mirea.docker.elitetickets.entities.UserEntity;
import ru.mirea.docker.elitetickets.enums.TypeReg;
import ru.mirea.docker.elitetickets.enums.UserRole;
import ru.mirea.docker.elitetickets.repositories.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserDao {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserModel getUserByEmail(String email){
        if(userRepository.findByEmail(email).isPresent()){
            return UserModel.fromEntity(userRepository.findByEmail(email).get());
        }
        return null;
    }

    public List<UserEntity> getAllUsers(){
        return userRepository.findAll();
    }

    public UserEntity getUserEntityByEmail(String email){
        return userRepository.findByEmail(email).orElseThrow();
    }

    public UserModel registerUser(UserModel userModel, String password){
        UserEntity user = UserEntity.builder()
                    .email(userModel.getEmail())
                    .password(passwordEncoder.encode(password))
                    .firstName(userModel.getFirstName())
                    .lastName(userModel.getLastName())
                    .birthDate(userModel.getBirthDate())
                    .role(UserRole.USER)
                    .regType(TypeReg.APP)
                    .registrationDate(LocalDateTime.now())
                    .lastAction(LocalDateTime.now())
                    .emailIsConfirmed(false)
                    .build();
        return UserModel.fromEntity(userRepository.save(user));
    }

    public boolean validateUser(String email, String password){
        UserEntity user = getUserEntityByEmail(email);

        return passwordEncoder.matches(password, user.getPassword());
    }

    public boolean userExistByEmail(String email){
        return userRepository.findByEmail(email).isPresent();
    }
}
