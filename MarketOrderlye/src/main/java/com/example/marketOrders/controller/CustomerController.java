package com.example.marketOrders.controller;

import com.example.marketOrders.DTO.CustomerDTO;
import com.example.marketOrders.entity.Customer;
import com.example.marketOrders.service.CustomerService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/customers")
public class CustomerController {
    final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public void createCustomer(@Valid @RequestBody CustomerDTO CustomerDTO) {
        customerService.createNewCustomer(CustomerDTO);
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
    // TODO: доработать метод поиск по мейлу. В целом весь класс контроллера надо типизировать
    @GetMapping("/search")
    public Customer searchMail(@RequestParam String mail) {
        return customerService.findByEmail(mail);
    }

    @GetMapping("/orders")
    public List<Customer> getCustomerOverNOrders(@RequestParam Integer minOrders) {
        List<Customer> customerList = customerService.getCustomerOverNOrders(minOrders);
        if (customerList.isEmpty()) throw new EntityNotFoundException("Нет Customer удовлетворяющих условию - "
                + " \\\"Заказов больше \\\" - " + minOrders);
        return customerList;
    }

}
