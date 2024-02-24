package ru.mirea.docker.elitetickets.repositories.redis;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.awt.image.BufferedImage;
import java.util.Map;

@Repository
public class RedisTicketImageRepository implements RedisRepository<String, BufferedImage> {

    private static final String KEY = "TicketImage";

    private final RedisTemplate<String, BufferedImage> redisTemplate;

    private HashOperations<String, String, BufferedImage> hashOperations;

    public RedisTicketImageRepository(@Qualifier("redisImageTemplate") RedisTemplate<String, BufferedImage> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    @PostConstruct
    public void init() {
        hashOperations = redisTemplate.opsForHash();
    }

    @Override
    public Map<String, BufferedImage> findAll() {
        return hashOperations.entries(KEY);
    }

    @Override
    public void add(String hash, BufferedImage value) {
        hashOperations.put(KEY, hash, value);
    }

    @Override
    public BufferedImage delete(String hash) {
        BufferedImage image = hashOperations.get(KEY, hash);
        hashOperations.delete(KEY, hash);
        return image;
    }

    @Override
    public BufferedImage findValueByHash(String hash) {
        return hashOperations.get(KEY, hash);
    }
}
