package ru.mirea.docker.elitetickets.configurations;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import ru.mirea.docker.elitetickets.utils.ImageSerializer;

import java.awt.image.BufferedImage;

@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RedisConfig {

    @Value("${spring.data.redis.database:1}")
    Integer redisDatabase;

    @Value("${spring.data.redis.password:}")
    String password;

    @Value("${spring.data.redis.host:localhost}")
    String host;

    @Value("${spring.data.redis.port:6379}")
    Integer port;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(host, port);

        redisStandaloneConfiguration.setPassword(password);

        redisStandaloneConfiguration.setDatabase(redisDatabase);

        return new JedisConnectionFactory(redisStandaloneConfiguration);
    }

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        return new JedisConnectionFactory();
    }

    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

    @Bean
    public RedisTemplate redisImageTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<String, BufferedImage> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setHashValueSerializer(new ImageSerializer());
        return template;
    }
}
