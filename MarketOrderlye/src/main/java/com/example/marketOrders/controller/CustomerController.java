package com.example.marketOrders.controller;

import com.example.marketOrders.entity.Customer;
import com.example.marketOrders.service.CustomerService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/customers")
public class CustomerController {
    final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // Создание нового Customer и сохранение его в базу, но если так посмотреть, то метод сразу отправляет на сохранение
    @PostMapping
    public void createCustomer(@RequestBody Customer customer) {
        // проверка на то, что хоть что-то пришло
        if (customer == null) throw new IllegalArgumentException("Кастомер без параметров");
        customerService.save(customer);
    }

    // получение пользователя по id
    @GetMapping("/{id}")
    public Customer getCustomer(@PathVariable Long id) {
        return customerService.findById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCustomer(@PathVariable @NotNull Long id, @RequestBody Customer customer) {
//        customerService.updateCustomer(id,customer);
        return ResponseEntity.ok("Customer update successfully");
    }

    // Удаление пользователя по id
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.ok("Customer deleted successfully");
    }

    @GetMapping
    public List<Customer> getAllCustomer() {
        return customerService.findAllCustomer();
    }

    // GET	/customers/search?emaiel={email}	Найти клиента по email
    @GetMapping("/search")
    public Customer searchMail(@RequestParam String mail) {
        return customerService.findByEmail(mail).orElseThrow(() ->
                new EntityNotFoundException("Customer not found with mail " + mail));
    }

    @GetMapping("/orders")
    public List<Customer> getCustomerOverNOrders(@RequestParam Integer minOrders) {
        List<Customer> customerList = customerService.getCustomerOverNOrders(minOrders);
        if (customerList.isEmpty()) throw new EntityNotFoundException("Нет Customer удовлетворяющих условию - "
                + " \\\"Заказов больше \\\" - " + minOrders);
        return customerList;
    }

}
