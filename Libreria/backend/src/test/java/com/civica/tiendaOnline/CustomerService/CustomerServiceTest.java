package com.civica.tiendaOnline.CustomerService;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.civica.tiendaOnline.model.Customer;
import com.civica.tiendaOnline.service.CustomerService;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @Test
    public void testGetAllCustomersByProductId() {
        List<Customer> customersWithProduct = customerService.findByOrdersOrderDetailProductId(1L);
        Assertions.assertNotEquals("Juan", customersWithProduct.get(0).getFirstName());
        Assertions.assertNotNull(customersWithProduct);
    }

}
