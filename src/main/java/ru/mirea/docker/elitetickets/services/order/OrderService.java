package ru.mirea.docker.elitetickets.services.order;

import ru.mirea.docker.elitetickets.dto.models.OrderModel;
import ru.mirea.docker.elitetickets.dto.requests.OrderRequest;

import java.util.List;

public interface OrderService {

    OrderModel createOrder(OrderRequest request);

    List<OrderModel> getOrders();

}
