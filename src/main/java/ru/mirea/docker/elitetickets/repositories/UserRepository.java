package ru.mirea.docker.elitetickets.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.mirea.docker.elitetickets.entities.UserEntity;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByEmail(String email);

    @Query("""
            SELECT user.password
            FROM UserEntity user
            WHERE user.email =:email
            """)
    Optional<String> getPasswordByEmail(String email);
}
