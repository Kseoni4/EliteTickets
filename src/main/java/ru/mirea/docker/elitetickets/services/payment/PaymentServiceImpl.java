package ru.mirea.docker.elitetickets.services.payment;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.mirea.docker.elitetickets.dao.OrderDao;
import ru.mirea.docker.elitetickets.dto.models.OrderModel;
import ru.mirea.docker.elitetickets.dto.models.PaymentModel;


@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService{

    private final OrderDao orderDao;

    private final YooKassaClient yooKassaClient;

    private final ObjectMapper objectMapper;

    @Override
    @SneakyThrows
    public PaymentModel createPayment(OrderModel orderModel) {
        ResponseEntity<String> yooKassaResponse = yooKassaClient.createPayment(orderModel);

        String body = yooKassaResponse.getBody();

        log.info(body);

        PaymentModel paymentModel = objectMapper.readValue(body, PaymentModel.class);

        log.info(paymentModel.toString());

        return paymentModel;
    }
}
