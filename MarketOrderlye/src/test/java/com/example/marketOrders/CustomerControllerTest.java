package com.example.marketOrders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.marketOrders.controller.CustomerController;
import com.example.marketOrders.entity.Customer;
import com.example.marketOrders.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


//@DataJpaTest  // Позволяет тестировать JPA-репозиторий в изолированном окружении
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@Transactional
@ExtendWith(MockitoExtension.class)
@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private CustomerService customerService;
//    @InjectMocks
//    private CustomerController customerController;
//
//    @BeforeEach
//    public void setUp(){
//        MockitoAnnotations.openMocks(this);
//        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
//    }

    @Test
    public void testCreateCustomer() throws Exception {
    Long id = 3L;
    doNothing().when(customerService).deleteCustomer(id);
//    mockMvc.perform(delete("/customer/{id}"),id).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isnoc)
        mockMvc.perform(delete("/customers/{id}", id)).andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    public void testDeleteCustomer() {
//        CustomerService customerService = new CustomerService(customerRepository);

        List<Customer> customers = customerService.filterName("Алексей Смирнов");
        System.out.println(customers.size());
        for (Customer customer : customers) {
            System.out.println(customer.getName());
        }

        System.out.println("____________");

        List<Customer> customers2 = customerService.hasNameContaining("йло");
        System.out.println(customers2.size());
        for (Customer customer : customers2) {
            System.out.println(customer.getName());
        }
        System.out.println("____________");



//        List<Customer> customers = customerService.getCustomerOverNOrders(2);
//        for(Customer customer: customers){
//            System.out.println(customer.getName() + "- ");
//            for(Order order :customer.getOrders()){
//                System.out.println(order);
//            }
//        }

//    assertThat(customerService.getCustomerByName("Van")).isEmpty();


//        Customer  customer = new Customer();
////        customerRepository.save(customer);  // Вполне рабочий способ сохранить но так лучше не делать
////        так как лучше работать через слой Сервиса таким образом
//        customerService.save(customer); // это считается более правильным подходом в практке программистов


//        Optional<T>  - Сущность
    }

    @Test
    public void testCustomerService() {
//        CustomerService customerService = new CustomerService(customerRepository);

//        assertTrue();
//        customerServic
//        customerService.getCustomerByName("Vax");


    }


}