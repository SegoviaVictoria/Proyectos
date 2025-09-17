package com.civica.tiendaOnline.service;

import java.util.List;

import com.civica.tiendaOnline.dto.CustomerDTO;
import com.civica.tiendaOnline.model.Customer;

public interface CustomerService {

    List<CustomerDTO> getAllCustomerDTOs();

    CustomerDTO getCustomerDTOById(Long id);

    CustomerDTO createCustomer(CustomerDTO customerDTO);

    CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO);

    void deleteCustomerById(Long id);

    List<Customer> findByOrdersOrderDetailProductId(Long productId);

}
