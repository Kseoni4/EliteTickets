package ru.mirea.docker.elitetickets.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "mail")
public class MailProperties {

    private String host;

    private String port;

    private String sender;

    private String password;

}
