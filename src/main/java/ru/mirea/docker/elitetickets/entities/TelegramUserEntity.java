package ru.mirea.docker.elitetickets.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "telegram_users")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TelegramUserEntity {

    @Id
    private Integer telegramId;

    @OneToOne
    private UserEntity user;
}
