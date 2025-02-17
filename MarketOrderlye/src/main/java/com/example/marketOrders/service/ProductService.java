package com.example.marketOrders.service;


import com.example.marketOrders.entity.Product;
import com.example.marketOrders.repository.ProductRepository;
import com.example.marketOrders.specification.ProductSpecification;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void createProduct(Product product) {
        validateProduct(product);
        if (productRepository.findByName(product.getName()).isPresent())
            throw new EntityNotFoundException("Продукт с названием - " + product.getName() + " уже существует.");

        productRepository.save(product);

        productRepository.findByName(product.getName()).orElseThrow(
                () -> new EntityNotFoundException("продукт не сохранился"));

    }

    private void validateProduct(Product product) {
        if (product == null) throw new IllegalArgumentException("Продукт пустой");
        if (product.getName().isEmpty()) throw new IllegalArgumentException("Имя не может быть пустым");
        if (product.getPrice() < 0) throw new IllegalArgumentException("Цена должна быть больше 0");
        if (product.getName().length() > 100)
            throw new IllegalArgumentException("Длинна имени превышает установленный диапазон");
        if (product.getDescription().length() > 500)
            throw new IllegalArgumentException("Длинна описания превышает установленный диапазон");
    }

    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Такого продукта нет"));
    }

    public Product findByName(String name) {
        return productRepository.findByName(name).orElseThrow(() ->
                new EntityNotFoundException("Такого продукта нет"));
    }

    // GOTO
    // Есть такой момент, у нас в классе продукт есть лист с заказами
    // необходимо создать метод для добавления заказа
    // так же и у CUSTOMER надо добавить метод по добавлению(созданию) заказов
    // а еще и удаление из заказа товаров
    // причем надо следить, заказ на какой стадии находится
    public void updateProduct(Product product, Long id) {

        Product productUpdate = productRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Продукт не был найден"));
        validateProduct(product);

        if (product.getName() != null) productUpdate.setName(productUpdate.getName());
        if (product.getImageUrl() != null) productUpdate.setImageUrl(productUpdate.getImageUrl());
        if (product.getPrice() > 0) productUpdate.setPrice(productUpdate.getPrice());
        if (product.getDescription() != null) productUpdate.setDescription(productUpdate.getDescription());

        productRepository.save(product);
    }

    // GOTO надо крайние ситуации обработать
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(()
                -> new EntityNotFoundException("Продукта не существует"));
        productRepository.delete(product);
    }


    public List<Product> filterProductInPrice(Integer minPrice, Integer maxPrice) {
        return productRepository.findAll(ProductSpecification.hasPriceBetween(minPrice, maxPrice));
    }



}
