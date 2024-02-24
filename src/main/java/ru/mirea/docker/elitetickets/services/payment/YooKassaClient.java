package ru.mirea.docker.elitetickets.services.payment;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.mirea.docker.elitetickets.dto.models.OrderModel;
import ru.mirea.docker.elitetickets.properties.PaymentProperties;

import java.util.Base64;
import java.util.UUID;

@Slf4j
@Builder
@RequiredArgsConstructor
@Component
public class YooKassaClient {

    private final static String YOOKASSA_URL = "https://api.yookassa.ru/v3/payments";
    private final static String CURRENCY = "RUB";

    private final static String PAYMENT_REQUEST_JSON = """
            {
              "amount": {
                      "value": "%d",
                      "currency": "%s"
                    },
                    "capture": true,
                    "confirmation": {
                      "type": "redirect",
                      "return_url": "%s"
                    },
                    "description": "%s"
            }
            """;
    private final PaymentProperties properties;
    private final RestTemplate restTemplate;

    public ResponseEntity<String> createPayment(OrderModel orderModel){
        String finalRequest = PAYMENT_REQUEST_JSON.formatted(
                orderModel.getTicket().getPrice(),
                CURRENCY,
                properties.getReturnUrl(),
                orderModel.getTicket().getEventName());

        HttpEntity<String> httpEntity = new HttpEntity<>(finalRequest, createHeaders(UUID.fromString(orderModel.getOrderId())));

        ResponseEntity<String> response = restTemplate.postForEntity(YOOKASSA_URL, httpEntity, String.class);

        log.info(response.toString());

        return response;
    }



    private HttpHeaders createHeaders(UUID orderId){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.add("Idempotence-Key", orderId.toString());
        log.info(properties.toString());
        httpHeaders.add("Authorization",
                "Basic " + Base64.getEncoder().encodeToString((properties.getShopId() + ":" + properties.getSecretKey()).getBytes()));

        return httpHeaders;
    }
}
