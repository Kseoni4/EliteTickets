package ru.mirea.docker.elitetickets;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.mirea.docker.elitetickets.dto.requests.OrderRequest;
import ru.mirea.docker.elitetickets.entities.EventEntity;
import ru.mirea.docker.elitetickets.entities.UserEntity;
import ru.mirea.docker.elitetickets.enums.TypeReg;
import ru.mirea.docker.elitetickets.enums.UserRole;
import ru.mirea.docker.elitetickets.repositories.EventRepository;
import ru.mirea.docker.elitetickets.repositories.UserRepository;
import ru.mirea.docker.elitetickets.services.order.OrderService;

import java.time.LocalDate;

/**
 * Класс для генерации тестовых сущностей в базу данных<br>
 * Генерация происходит в случае, если в БД нет тестового пользователя<br>
 * Создаёт тестового пользователя с данными:<br>
 * <b>email:</b> testuser@mail.com<br>
 * <b>password:</b> qwerty123<br>
 * <b>user role:</b> USER<br>
 * <b>registration type:</b> APP<br>
 *
 * <br>
 *
 * Создаёт тестовое мероприятие с данными:<br>
 * <b>Event name:</b> Test event<br>
 */
@Component
@RequiredArgsConstructor
public class DataInitializer {

    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final OrderService orderService;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    void generateTestData(){

        if(userRepository.findByEmail("jsmith@mail.com").isPresent()){
            return;
        }

        UserEntity user1 = UserEntity
                .builder()
                .firstName("John")
                .lastName("Smith")
                .email("jsmith@mail.com")
                .birthDate(LocalDate.of(1990, 1, 1))
                .password(passwordEncoder.encode("qwerty123"))
                .role(UserRole.USER)
                .regType(TypeReg.APP)
                .emailIsConfirmed(false)
                .build();

        userRepository.save(user1);

        if(userRepository.findByEmail("testuser@mail.com").isPresent()){
            return;
        }

       UserEntity user = UserEntity
                .builder()
                .email("testuser@mail.com")
                .password("qwerty123")
                .role(UserRole.USER)
                .regType(TypeReg.APP)
                .emailIsConfirmed(false)
                .build();

       userRepository.save(user);

       EventEntity event = EventEntity.builder()
                .eventName("Test event")
                .build();

       eventRepository.save(event);
    }

}
