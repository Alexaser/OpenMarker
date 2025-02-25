package com.example.marketOrders.service;

import com.example.marketOrders.entity.Customer;
import com.example.marketOrders.entity.Order;
import com.example.marketOrders.entity.OrderItem;
import com.example.marketOrders.entity.enums.StatusOrder;
import com.example.marketOrders.repository.CustomerRepository;
import com.example.marketOrders.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    public OrderService(OrderRepository orderRepository, CustomerRepository customerRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
    }

    // создаю заказ, мне не важно существуют ли заказы с таким же статусом у данного пользователя
    // мне важно что есть такой пользователь, но я сомневаюсь что его можно так принять в качестве аргумента
    public Order createOrder(Long customerId, List<OrderItem> orderItemList) {

        if (orderItemList.isEmpty()) {
            logger.warn("Список заказов пуст");
            throw new IllegalArgumentException("Order item list cannot be empty");
        }

        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> {
            logger.warn("Такого пользователя нет с id : {}", customerId);  // надо описать лог
            return new EntityNotFoundException("Customer not found");
        });

        Order order = new Order();
        // вначале присваиваем имя заказу зависящее от даты, чтобы если будет какая ошибка, хоть по дате идентифицировать
        order.setName("Заказ от " +
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")));
        order.setStatusOrder(StatusOrder.NEW);
        orderRepository.save(order);

        order.setName("Заказ № - " + order.getId() + " от " +
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")));

        for (OrderItem item : orderItemList) {
            item.setOrder(order);
        }
        order.setOrderItems(orderItemList);
        orderRepository.save(order);

        logger.info("New order created. Order ID: {}, Customer ID: {}", order.getId(), customerId);
        return order;
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
                throw new IllegalArgumentException("Нельзя изменить статус с "
                        + orderInDataBase.getStatusOrder() + " на " + order.getStatusOrder());

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

    // насчет делита заказа, я считаю что в базе данных для внутренней отчетности должен храниться заказ
    // А пользователь не может удалять заказы НЕ имеющие статус (Создан или отменен)
    // Хочу реализовать это удаление с возвращаемым String записью о статусе удаления заказа
    // метод получился полным, по этому примеру должны быть выполнены все остальные =)
    public String deleteOrder(Long orderId, Long customerId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> {
                    logger.warn("Order not found. ID: {}", orderId);
                    return new EntityNotFoundException("Order no found");
                });

        // Проверяем, принадлежит ли заказ пользователю
        if (order.getCustomer().getId().equals(customerId)) {
            logger.warn("Unauthorized delete attempt. Customer ID: {}, Order ID: {}", customerId, orderId);
            throw new SecurityException("Unauthorized attempt to delete an order");
        }

        StatusOrder statusOrder = order.getStatusOrder();
        // Проверка - из какого статуса хотят удалить заказ
        // предлагаю не удалять заказ у которого статус комплетед, так как это история заказов уже
        if (statusOrder == StatusOrder.PENDING_PAYMENT || statusOrder == StatusOrder.PROCESSING
                || statusOrder == StatusOrder.SHIPPED || statusOrder == StatusOrder.COMPLETED) {
            logger.warn("Attempt to delete an order in restricted status. Order ID: {}, Status: {}", orderId,
                    statusOrder.getDescription());
            throw new IllegalArgumentException("Cannot delete an order with status " + statusOrder.getDescription());
        }

        // Далее удаляем заказ только у данного пользователя, в базе заказ будет числиться
        order.setDeleted(true);
        orderRepository.save(order);

        logger.info("Order successfully deleted. Order ID: {}, Customer ID: {}", orderId, customerId);
        return "Order successfully deleted";

    }
}
