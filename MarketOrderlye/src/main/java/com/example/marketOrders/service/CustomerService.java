package com.example.marketOrders.service;


import com.example.marketOrders.entity.Customer;
import com.example.marketOrders.repository.CustomerRepository;
import com.example.marketOrders.specification.CustomerSpecification;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;
import java.util.Optional;


@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    // Сохранение нового пользователя
    public Customer save(Customer customer) {
        String number = numberTelephoneValidationRussianFederation(customer.getPhone());
        String mail = mailValidation(customer.getEmail());

        //GOTO
        // думаю нужно добавить проверку по тому, встречается ли такой mail и номер телефона в базе, только потом сохранять

        Customer customerUp = new Customer(customer.getId(), customer.getName(), mail
                , number, customer.getOrders());
        return customerRepository.save(customerUp);
    }

    // Валидация номера
    // представим что я хочу чтобы все номера были в формате 79870072088
    // так же было бы здорово если бы пользователю из бэка приходила информация об ошибках ("
    // реализовал в package controllerExceptionAdvice class - GlobalExceptionHandler method - handleIllegalArgumentException
    // возвращаемое значение String оставлю для возможности корректировки номера под формат
    public String numberTelephoneValidationRussianFederation(String number) {
        if (number == null || number.isEmpty()) throw new IllegalArgumentException("Неверное количество цифр в номере");
        if (number.length() != 11) throw new IllegalArgumentException("Неверное количество цифр в номере");
        if (number.matches("^7\\d{10}$"))
            throw new IllegalArgumentException("Номер должен начинаться с 7 и содержать только 11 цифр");
        if (!number.matches("\\d+")) throw new IllegalArgumentException("Номер должен содержать только цифры");
        return number;
    }

    // Валидация mail
    public String mailValidation(String mail) {
        if (!mail.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"))
            throw new IllegalArgumentException("Invalid email format");
        return mail;
    }

    // Поиск по id
    public Customer findById(Long id) {

        return customerRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
//        return customerRepository.findById(id);
    }

    // Поиск по email
    public Optional<Customer> findByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    // Поиск по имени
    public Optional<Customer> findByName(String name) {
        return customerRepository.findByName(name);
    }

    // Обновление данных клиента // я на самом деле сомневаюсь в данном месте,
    // Разве мы когда обновляем передаем это как сущность у которой можем взять id?
    public String updateCustomer(Customer customer, Long id) {
        if (id == null) throw new IllegalArgumentException("Customer ID must not be null for an update");
// поиск в базе с таким id
        var customer1 = customerRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Customer with" +
                        "id (" + id + ") not found!"));

        if (customer.getEmail() != null) customer1.setEmail(mailValidation(customer.getEmail()));
        if (customer.getName() != null) customer1.setName(customer.getName());
        if (customer.getPhone() != null) customer1
                .setPhone(numberTelephoneValidationRussianFederation(customer.getPhone()));
        if (customer.getOrders() != null && customer.getOrders().isEmpty()) customer1.setOrders(customer.getOrders());
        customerRepository.save(customer1);
        return "Customer update";
    }

    // Удаление пользователя по id
    @Transactional
    public void deleteCustomer(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new EntityNotFoundException("Customer not found");
        }
        customerRepository.deleteCustomerById(id);
    }

    // Получение списка всех клиентов
    public List<Customer> findAllCustomer() {
        return customerRepository.findAll();
    }

    // Поиск клиентов у которых больше N заказов
    public List<Customer> getCustomerOverNOrders(Integer n) {
        if (n == 0) throw new IllegalArgumentException("The number of orders must be greater than 0");
        return customerRepository.findAllByCustomerOrdersOverN(n);
    }

    // фильтрация поиска по целому имени
    public List<Customer> filterName(String name) {
        return customerRepository.findAll(CustomerSpecification.hashName(name));
    }

    // поиск по части в имени
    public List<Customer> hasNameContaining(String trimName) {
        return customerRepository.findAll(CustomerSpecification.hasNameContaining(trimName));
    }


//    public List<Customer> filterCustomer(){
//        customerRepository.findAll(where())
//    }


}
