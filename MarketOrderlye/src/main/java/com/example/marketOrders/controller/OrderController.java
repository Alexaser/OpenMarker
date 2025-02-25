package com.example.marketOrders.controller;

import com.example.marketOrders.entity.OrderItem;
import com.example.marketOrders.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public void createOrder(@RequestBody Long customerId, @RequestBody List<OrderItem> orderItemList) {
        orderService.createOrder(customerId, orderItemList);
    }
}
