package ru.mirea.docker.elitetickets.controllers.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.mirea.docker.elitetickets.dto.models.OrderModel;
import ru.mirea.docker.elitetickets.dto.models.PaymentModel;
import ru.mirea.docker.elitetickets.dto.requests.OrderRequest;
import ru.mirea.docker.elitetickets.dto.response.CreatedOrderResponse;
import ru.mirea.docker.elitetickets.services.order.OrderService;
import ru.mirea.docker.elitetickets.services.payment.PaymentService;

import java.util.List;

@RestController
@RequestMapping("/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    private final PaymentService paymentService;

    @PostMapping("")
    public PaymentModel createOrder(@RequestBody OrderRequest request){
       OrderModel orderModel = orderService.createOrder(request);
       return paymentService.createPayment(orderModel);
    }

    @GetMapping("")
    public List<OrderModel> getOrders(){
        return orderService.getOrders();
    }
}
