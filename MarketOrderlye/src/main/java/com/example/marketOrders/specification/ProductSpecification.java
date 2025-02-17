package com.example.marketOrders.specification;

import com.example.marketOrders.entity.Order;
import com.example.marketOrders.entity.Product;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecification {


    public static Specification<Product> nameContains(String trim) {
        return ((root, query, criteriaBuilder) -> {
            if (trim == null) return criteriaBuilder.conjunction();

            String trimName = "%" + trim.toLowerCase() + "%".toLowerCase();
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), trimName);
        });
    }

    // фильтрация по диапазон мин-макс
    public static Specification<Product> hasPriceBetween(Integer minPrice, Integer maxPrice) {
        return ((root, query, criteriaBuilder) -> {
            return criteriaBuilder.between(root.get("price"), minPrice, maxPrice);
        }
        );
    }

//    public static Specification<Product>
}
