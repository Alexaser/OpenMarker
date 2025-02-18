package com.example.marketOrders.service;


import com.example.marketOrders.entity.Customer;
import com.example.marketOrders.repository.CustomerRepository;
import com.example.marketOrders.specification.CustomerSpecification;
import com.example.marketOrders.validator.CustomerValidator;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;


@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerValidator customerValidator;
    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);

    @Autowired
    public CustomerService(CustomerRepository customerRepository, CustomerValidator customerValidator) {
        this.customerRepository = customerRepository;
        this.customerValidator = customerValidator;

    }

    // Сохранение нового пользователя
    public Customer save(Customer customer) {
        customerValidator.validatePhone(customer.getPhone());
        customerValidator.validationEmail(customer.getEmail());

        //GOTO
        // думаю нужно добавить проверку по тому, встречается ли такой mail и номер телефона в базе, только потом сохранять

        Customer customerUp = new Customer(customer.getId(), customer.getName(), customer.getEmail()
                , customer.getPhone(), customer.getOrders());
        return customerRepository.save(customerUp);
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

    // метод доработан, улучшена защита от удаления нужных данных
    // Добавлено логирование
    @Transactional
    public Customer updateCustomer(Long id, Map<String, Object> updates) {

        Customer customer = customerRepository.findById(id).orElseThrow(() -> {
            logger.warn("Customer not found with ID: {}", id);
            return new EntityNotFoundException("Customer not found with ID:" + id);
        });

        boolean updated = false;

        // Обновление мейла решил сделать таким образом, но мне кажется он напрасно усложненным сложным
        if (updates.containsKey("email")) {
            Object emailObject = updates.get("email");
            if (emailObject instanceof String) {
                String email = (String) emailObject;
                customerValidator.validationEmail(email);
                customer.setEmail(email);
            } else {
                throw new IllegalArgumentException("invalid email format. Expected a string.");
            }
            updated = true;
        }

        if (updates.containsKey("name")) {
            customer.setName((String) updates.get("name"));
            updated = true;
        }

        if (updates.containsKey("phone")) {
            String phone = (String) updates.get("phone");
            customerValidator.validatePhone(phone);
            customer.setPhone(phone);
            updated = true;
        }

        if (!updated) {
            logger.warn("No changes detected for customer ID: {}", id);
        }

        customerRepository.save(customer);
        logger.info("Customer updated: ID: {} , Changes: {} ", id, updates);

        return customer;
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


/* Валидация номера - переехала в дополнительный слой customerValidation
 представим что я хочу чтобы все номера были в формате 79870072088
 так же было бы здорово если бы пользователю из бэка приходила информация об ошибках ("
 реализовал в package controllerExceptionAdvice class - GlobalExceptionHandler method - handleIllegalArgumentException
 возвращаемое значение String оставлю для возможности корректировки номера под формат
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
*/

