package ru.mirea.docker.elitetickets.controllers.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.mirea.docker.elitetickets.dto.models.OrderModel;
import ru.mirea.docker.elitetickets.dto.requests.OrderRequest;
import ru.mirea.docker.elitetickets.dto.response.CreatedOrderResponse;
import ru.mirea.docker.elitetickets.services.order.OrderService;

import java.util.List;

@RestController
@RequestMapping("/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("")
    public CreatedOrderResponse createOrder(@RequestBody OrderRequest request){
       OrderModel orderModel = orderService.createOrder(request);
       return CreatedOrderResponse.builder()
               .paymentStatus(orderModel.getPaymentStatus().name())
               .orderId(orderModel.getOrderId())
               .build();
    }

    @GetMapping("")
    public List<OrderModel> getOrders(){
        return orderService.getOrders();
    }
}
