package com.example.marketOrders.DTO;



import com.example.marketOrders.entity.Customer;
import com.example.marketOrders.entity.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public class CustomerDTO {

//    @NotBlank(message = "Name is required")
    private String name;

//    @Email(message = "Invalid email format")
    private String email;

//    @Pattern(regexp = "^7\\d{10}$", message = "Invalid phone number format. Must start with 7 and contain 11 digits.")
    private String phone;

    private String role;


    public CustomerDTO(Customer customer) {
        this.name = customer.getName();
        this.email = customer.getEmail();
        this.phone = customer.getPhone();
        this. role = customer.getRole().name();
    }
}
