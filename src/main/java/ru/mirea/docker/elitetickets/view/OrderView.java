package ru.mirea.docker.elitetickets.view;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import ru.mirea.docker.elitetickets.dto.models.OrderModel;
import ru.mirea.docker.elitetickets.services.order.OrderService;

import static java.util.stream.Collectors.*;

@Route("/orders")
@AnonymousAllowed
public class OrderView extends VerticalLayout {

    private final OrderService orderService;

    public OrderView(@Autowired OrderService orderService) {
        this.orderService = orderService;
        VerticalLayout orderList = new VerticalLayout();
        orderList.add(orderService.getOrders().stream().map(OrderModel::toString).map(Span::new).collect(toList()));
        add(
                new H1("Заказы"),
                orderList
        );
    }

}
