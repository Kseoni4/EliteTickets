package ru.mirea.docker.elitetickets.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "payments")
public class PaymentProperties {

    private String secretKey;

    private String shopId;

    private String returnUrl;

}
