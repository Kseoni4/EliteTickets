package ru.mirea.docker.elitetickets.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.mirea.docker.elitetickets.entities.TelegramUserEntity;
import ru.mirea.docker.elitetickets.repositories.TgUserRepository;

@Component
@RequiredArgsConstructor
public class TgUserDao {

    private final TgUserRepository tgUserRepository;

    private final UserDao userDao;

    public TelegramUserEntity registerUser(Long userId, String email){
       if(!userDao.userExistByEmail(email)){
           throw new UsernameNotFoundException("User not found");
       }

       if(!tgUserRepository.existsById(Math.toIntExact(userId))){
           return tgUserRepository.findById(Math.toIntExact(userId)).orElseThrow();
       }

       TelegramUserEntity user = TelegramUserEntity.builder()
               .telegramId(Math.toIntExact(userId))
               .user(userDao.getUserEntityByEmail(email))
               .build();
       return tgUserRepository.save(user);
    }

}
