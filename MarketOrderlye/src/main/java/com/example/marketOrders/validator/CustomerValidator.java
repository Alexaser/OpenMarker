package com.example.marketOrders.validator;

import com.example.marketOrders.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;
@Component
public class CustomerValidator {
    private static final Logger logger = LoggerFactory.getLogger(CustomerValidator.class);
    private static final Pattern PHONE_PATTERN = Pattern.compile("^7\\d{10}$");
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");

    private final CustomerRepository customerRepository;

    public CustomerValidator(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void validatePhone(String phone) {
        if (phone == null || phone.isEmpty()) {
            logger.warn("Phone validation failed: No phone number provided");
            throw new IllegalArgumentException("Phone number is required");
        }
        if (!PHONE_PATTERN.matcher(phone).matches()) {
            logger.warn("Invalid phone format: '{}'. Expected format: 7XXXXXXXXXX.", phone);
            throw new IllegalArgumentException("Invalid phone number format. Must start with 7 and contain 11 digits.");
        }
        logger.info("Phone validation successful: {}", phone);
    }

    public void validationEmail(String email) {
        if (email == null || email.isEmpty()) {
            logger.warn("Email validation failed: No email provided");
            throw new IllegalArgumentException("Email number is required");
        }
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            logger.warn("Invalid email format: '{}'. Expected format: example@domain.com.", email);
            throw new IllegalArgumentException("Invalid email format.");
        }
        logger.info("Email validation successful: {}", email);
    }

}
