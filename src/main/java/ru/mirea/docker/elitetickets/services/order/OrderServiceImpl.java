package ru.mirea.docker.elitetickets.services.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mirea.docker.elitetickets.dao.EventDao;
import ru.mirea.docker.elitetickets.dao.OrderDao;
import ru.mirea.docker.elitetickets.dao.TicketDao;
import ru.mirea.docker.elitetickets.dao.UserDao;
import ru.mirea.docker.elitetickets.dto.models.OrderModel;
import ru.mirea.docker.elitetickets.dto.requests.OrderRequest;
import ru.mirea.docker.elitetickets.entities.EventEntity;
import ru.mirea.docker.elitetickets.entities.OrderEntity;
import ru.mirea.docker.elitetickets.entities.TicketEntity;
import ru.mirea.docker.elitetickets.entities.UserEntity;
import ru.mirea.docker.elitetickets.enums.PaymentStatus;
import ru.mirea.docker.elitetickets.enums.TicketType;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderDao orderDao;
    private final UserDao userDao;
    private final EventDao eventDao;
    private final TicketDao ticketDao;

    @Override
    public OrderModel createOrder(OrderRequest request) {
        UserEntity user = userDao.getUserEntityByEmail(request.getEmail());

        EventEntity event = eventDao.getEventFromName(request.getEventName());

        TicketEntity ticket = TicketEntity.builder()
                .owner(user)
                .event(event)
                .ticketType(TicketType.valueOf(request.getTicketType()))
                .price(request.getPrice())
                .isRedeemed(false)
                .build();

        ticket = ticketDao.createTicket(ticket);

        OrderEntity order = OrderEntity.builder()
                .owner(user)
                .ticket(ticket)
                .paymentStatus(PaymentStatus.CREATED)
                .build();

        return orderDao.createOrder(order, ticket);
    }

    @Override
    public List<OrderModel> getOrders() {
        return orderDao.getOrders();
    }
}
