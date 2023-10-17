package ru.mirea.docker.elitetickets;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.mirea.docker.elitetickets.entities.UserEntity;
import ru.mirea.docker.elitetickets.enums.TypeReg;
import ru.mirea.docker.elitetickets.enums.UserRole;
import ru.mirea.docker.elitetickets.repositories.UserRepository;

@Component
@RequiredArgsConstructor
public class DataInitializer {

    private final UserRepository userRepository;

    @PostConstruct
    void generateTestData(){
       UserEntity user = UserEntity
                .builder()
                .email("mail@mail.com")
                .password("qwerty")
                .role(UserRole.USER)
                .regType(TypeReg.APP)
                .build();

       userRepository.save(user);

    }

}
