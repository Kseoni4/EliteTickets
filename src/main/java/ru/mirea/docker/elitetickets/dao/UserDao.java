package ru.mirea.docker.elitetickets.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserDao {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserModel getUserByEmail(String email){
        return UserModel.fromEntity(userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("User not found")
        ));
    }

    public List<UserEntity> getAllUsers(){
        return userRepository.findAll();
    }

    public UserEntity getUserEntityByEmail(String email){
        return userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("User not found")
        );
    }


    public UserModel registerYandexUser(UserModel userModel, String password){
        return register(userModel, TypeReg.YANDEX, password);
    }

    public UserModel registerAppUser(UserModel userModel, String password){
        return register(userModel, TypeReg.APP, password);
    }

    private UserModel register(UserModel userModel, TypeReg typeReg, String password){
        UserEntity user = UserEntity.builder()
                    .email(userModel.getEmail())
                    .password(passwordEncoder.encode(password))
                    .firstName(userModel.getFirstName())
                    .lastName(userModel.getLastName())
                    .birthDate(userModel.getBirthDate())
                    .role(UserRole.USER)
                    .regType(typeReg)
                    .registrationDate(LocalDateTime.now())
                    .lastAction(LocalDateTime.now())
                    .emailIsConfirmed(false)
                    .build();
        return UserModel.fromEntity(userRepository.save(user));
    }

    public boolean validateUser(String email, String password){
        Optional<String> userPasswordOpt = userRepository.getPasswordByEmail(email);

        String userPassword = userPasswordOpt.orElseThrow();

        return passwordEncoder.matches(password, userPassword);
    }

    public boolean userExistByEmail(String email){
        return userRepository.findByEmail(email).isPresent();
    }
}
