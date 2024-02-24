package ru.mirea.docker.elitetickets.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mirea.docker.elitetickets.entities.TelegramUserEntity;

public interface TgUserRepository extends JpaRepository<TelegramUserEntity, Integer> {
}
