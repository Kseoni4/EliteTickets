package ru.mirea.docker.elitetickets.enums;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

public enum UserRole {

    USER,
    ADMIN,
    EVENT_OWNER
}
