package ru.mirea.docker.elitetickets.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.mirea.docker.elitetickets.dto.models.OrderModel;
import ru.mirea.docker.elitetickets.dto.models.TicketModel;
import ru.mirea.docker.elitetickets.dto.requests.OrderRequest;
import ru.mirea.docker.elitetickets.entities.OrderEntity;
import ru.mirea.docker.elitetickets.entities.TicketEntity;
import ru.mirea.docker.elitetickets.repositories.OrderRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderDao {

    private final OrderRepository repository;

    public OrderModel createOrder(OrderEntity entity, TicketEntity ticket) {
        OrderEntity order = repository.save(entity);
        return OrderModel.builder()
                .orderId(order.getId().toString())
                .ticket(TicketModel.fromEntity(ticket))
                .paymentStatus(order.getPaymentStatus())
                .build();
    }

    public List<OrderModel> getOrders(){
       return repository.findAll().stream().map(OrderModel::fromEntity).toList();
    }
}
