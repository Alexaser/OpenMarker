package com.example.marketOrders.repository;


import com.example.marketOrders.entity.Customer;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {
//    Customer sa

    Optional<Customer> findByEmail(String email);

    Optional<Customer> findByName(String name);

//    Customer removeCustomerById(Long id);

    void deleteCustomerById(Long id);
//    void removeCustomerById(Long id);

    @Query(value = """
            Select c from Customer c
            Where size(c.orders) >=:n
            """)

    List<Customer> findAllByCustomerOrdersOverN(@Param("n") Integer n);

    List<Customer> findAll(Specification<Customer> specification);
}
