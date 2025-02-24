package com.example.marketOrders.service;


import com.example.marketOrders.DTO.CustomerDTO;
import com.example.marketOrders.entity.Customer;
import com.example.marketOrders.repository.CustomerRepository;
import com.example.marketOrders.specification.CustomerSpecification;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    // За валидацию теперь ответсвенен Spring и добавлен новый слой customerDTO
    public Customer createNewCustomer(CustomerDTO customerDTO) {

        // Проверяю, есть ли такой емейл у другого пользователя в базе
        if (customerRepository.findByEmail(customerDTO.getEmail()).isPresent()) {
            logger.warn("User with email: {} already exists", customerDTO.getEmail());
            throw new IllegalArgumentException("Email already exists.");
        }

        // Проверяю, есть ли такой номер у другого пользователя в базе
        if (customerRepository.findByPhone(customerDTO.getPhone()).isPresent()) {
            logger.warn("User with phone: {} already exists", customerDTO.getPhone());
            throw new IllegalArgumentException("Phone already exists.");
        }
        // Создание нового пользователя
        Customer customer = new Customer();
        customer.setPhone(customerDTO.getPhone());
        customer.setName(customerDTO.getName());
        customer.setEmail(customerDTO.getEmail());

        logger.info("Сохранен новый пользователь: {}", customer);
        return customerRepository.save(customer);
    }

    // Поиск по id
    public Customer findById(Long id) {
        return customerRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
    }

    // Поиск по email
    public Customer findByEmail(String email) {
        return customerRepository.findByEmail(email).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
    }

    // Поиск по имени
    public Customer findByName(String name) {
        return customerRepository.findByName(name).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
    }

    // метод доработан, улучшена защита от удаления нужных данных
    // Добавлено логирование
    // TODO: требует рефакторинг так как метод валидации перешел в распоряжение спрингу
    @Transactional
    public Customer updateCustomer(Long id, CustomerDTO customerDTO) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> {
            logger.warn("Customer not found with ID: {}", id);
            return new EntityNotFoundException("Customer not found with ID:" + id);
        });

        // Проверка не находится ли в базе такой же емейл другого пользователя
        customerRepository.findByEmail(customerDTO.getEmail())
                .filter(customer1 -> !customer1.getId().equals(id))
                .ifPresent(existing -> {
                    logger.warn("Email {} is already reserved in the system by another user", customerDTO.getEmail());
                    throw new IllegalArgumentException("Email is already in the database");
                });

        // Проверка не находится ли в базе такой же телефонный номер другого пользователя
        customerRepository.findByPhone(customerDTO.getPhone())
                .filter(customer1 -> !customer1.getId().equals(id))
                .ifPresent(existing -> {
                    logger.warn("Phone {} is already reserved in the system by another user", customerDTO.getPhone());
                    throw new IllegalArgumentException("Phone is already in the database");
                });

        if (!customerDTO.getName().isEmpty()) {
            customer.setName(customerDTO.getName());
        }

        customerRepository.save(customer);
        logger.info("Customer updated: ID: {} , Changes: {} ", id, customerDTO);

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

/*  обновление полей в customer и валидация
//        boolean updated = false;
//        // Обновление мейла решил сделать таким образом, но мне кажется он напрасно усложненным сложным
//        if (updates.containsKey("email")) {
//            Object emailObject = updates.get("email");
//            if (emailObject instanceof String) {
//                String email = (String) emailObject;
//                customerValidator.validationEmail(email);
//                customer.setEmail(email);
//            } else {
//                throw new IllegalArgumentException("invalid email format. Expected a string.");
//            }
//            updated = true;
//        }
//        if (updates.containsKey("name")) {
//            customer.setName((String) updates.get("name"));
//            updated = true;
//        }
//        if (updates.containsKey("phone")) {
//            String phone = (String) updates.get("phone");
//            customerValidator.validatePhone(phone);
//            customer.setPhone(phone);
//            updated = true;
//        }
//        if (!updated) {
//            logger.warn("No changes detected for customer ID: {}", id);
//        }
//        customerRepository.save(customer);
//        logger.info("Customer updated: ID: {} , Changes: {} ", id, updates);
//
 */
