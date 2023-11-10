package ru.mirea.docker.elitetickets.enums;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {

    USER,
    ADMIN,
    EVENT_OWNER;

    @Override
    public String getAuthority() {
        return this.name();
    }
}
