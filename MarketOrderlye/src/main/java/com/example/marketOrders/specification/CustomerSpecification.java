package com.example.marketOrders.specification;

import com.example.marketOrders.entity.Customer;
import org.springframework.data.jpa.domain.Specification;


public class CustomerSpecification {

    // Спецификация для поиска клинтов по имени
    public static Specification<Customer> hashName(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("name"), name);
    }
    // поиск по символу, любое совпадение в столбце имя
    public static Specification<Customer> hasNameContaining(String trim) {
        return (root, query, criteriaBuilder) -> {
            if (trim == null) {
                return criteriaBuilder.conjunction();
            }
            String string = "%" + trim.trim().toLowerCase() + "%";
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), string);
        };
    }

}

