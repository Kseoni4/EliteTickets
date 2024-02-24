package ru.mirea.docker.elitetickets.services.payment;

import ru.mirea.docker.elitetickets.dto.models.OrderModel;
import ru.mirea.docker.elitetickets.dto.models.PaymentModel;

public interface PaymentService {

    PaymentModel createPayment(OrderModel orderModel);

}
