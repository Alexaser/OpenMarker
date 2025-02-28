package com.example.marketOrders.repository;


import com.example.marketOrders.entity.Customer;
import com.example.marketOrders.entity.Order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    public List<Order> findByCustomer(Customer customer);
    
}
