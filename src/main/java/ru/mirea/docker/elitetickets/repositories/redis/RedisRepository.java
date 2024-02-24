package ru.mirea.docker.elitetickets.repositories.redis;

import jakarta.annotation.PostConstruct;

import java.util.Map;

public interface RedisRepository<K, V> {

    @PostConstruct
    void init();

    Map<K, V> findAll();

    void add(K hash, V value);

    V delete(K hash);

    V findValueByHash(K hash);

}
