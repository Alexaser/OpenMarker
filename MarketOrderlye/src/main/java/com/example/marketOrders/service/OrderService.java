package com.example.marketOrders.service;

import com.example.marketOrders.entity.Customer;
import com.example.marketOrders.entity.Order;
import com.example.marketOrders.entity.OrderItem;
import com.example.marketOrders.entity.StatusOrder;
import com.example.marketOrders.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    // создаю заказ, мне не важно существуют ли заказы с таким же статусом у данного пользователя
    // мне важно что есть такой пользователь, но я сомневаюсь что его можно так принять в качестве аругмента
    public void createOrder(Customer customer, List<OrderItem> orderItemList) {
        if (orderItemList.isEmpty()) throw new EntityNotFoundException("Список товаров пуст");
        if (customer == null) throw new EntityNotFoundException("Пользователя не существует");

        Order order = new Order();
        order.setName("Заказ № - " + order.getId() + " от " +
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")));
        order.setStatusOrder(StatusOrder.NEW);
        order.setCustomer(customer);
        order.setOrderItems(orderItemList);

        orderRepository.save(order);
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Такого заказа не существует"));
    }

    public List<Order> getOrdersByCustomer(Customer customer) {
        List<Order> orderList = orderRepository.findByCustomer(customer);
        if (orderList.isEmpty()) throw new EntityNotFoundException("Заказов нет");
        return orderRepository.findByCustomer(customer);

    }

    public void updateOrder(Order order, Long id) {
        Order orderInDataBase = getOrderById(id);

        if (order.getOrderItems() != null && order.getOrderItems().isEmpty())
            orderInDataBase.setOrderItems(order.getOrderItems());
        if (order.getStatusOrder() != null)
            if (!updateStatus(order.getStatusOrder(), orderInDataBase.getStatusOrder()))
                throw new IllegalArgumentException("Нельзя изменить статус с " + orderInDataBase.getStatusOrder() + " на " +
                        order.getStatusOrder());

        orderRepository.save(orderInDataBase);
    }

    private boolean updateStatus(StatusOrder statusNew, StatusOrder statusOrderThis) {
        return switch (statusOrderThis) {
            case NEW -> statusNew == StatusOrder.PROCESSING
                    || statusNew == StatusOrder.PENDING_PAYMENT
                    || statusNew == StatusOrder.CANCELED;
            case PENDING_PAYMENT -> statusNew == StatusOrder.PROCESSING
                    || statusNew == StatusOrder.CANCELED;
            case PROCESSING -> statusNew == StatusOrder.SHIPPED || statusNew == StatusOrder.CANCELED;
            case SHIPPED -> statusNew == StatusOrder.COMPLETED;
            case COMPLETED, CANCELED -> false;
        };
    }

}
