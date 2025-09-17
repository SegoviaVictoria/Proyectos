package com.civica.tiendaOnline.service.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.civica.tiendaOnline.dto.CustomerDTO;
import com.civica.tiendaOnline.model.Customer;
import com.civica.tiendaOnline.model.Order;
import com.civica.tiendaOnline.model.OrderDetail;
import com.civica.tiendaOnline.model.Product;
import com.civica.tiendaOnline.repository.CustomerRepository;
import com.civica.tiendaOnline.service.CustomerService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public List<CustomerDTO> getAllCustomerDTOs() {
        return customerRepository.findAll().stream()
                .map(this::toCustomerDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO getCustomerDTOById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer with ID " + id + " not found"));
        return toCustomerDTO(customer);
    }

    @Override
    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        validateCustomer(customerDTO);

        Customer newCustomer = new Customer(
                customerDTO.getId(),
                customerDTO.getFirstName(),
                customerDTO.getLastName(),
                customerDTO.getEmail(),
                customerDTO.getPhone()
        );

        Customer savedCustomer = customerRepository.save(newCustomer);

        return toCustomerDTO(savedCustomer);
    }

    @Override
    public CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO) {
        validateExistingCustomer(id, customerDTO);

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer with id " + id + " not found"));

        customer.setFirstName(customerDTO.getFirstName());
        customer.setLastName(customerDTO.getLastName());
        customer.setEmail(customerDTO.getEmail());
        customer.setPhone(customerDTO.getPhone());
        customer.setRegistrationDate(customerDTO.getRegistrationDate());
        customer.setActive(customerDTO.isActive());

        Customer updated = customerRepository.save(customer);
        return toCustomerDTO(updated);
    }

    @Override
    public void deleteCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer with id " + id + " not found."));

        if (customer.getOrders() != null && !customer.getOrders().isEmpty()) {
            throw new IllegalStateException("Cannot delete customer with existing orders.");
        }

        customerRepository.deleteById(id);
    }

    @Transactional
    @Override
    public List<Customer> findByOrdersOrderDetailProductId(Long productId) {

        List<Customer> customers = customerRepository.findAll();
        List<Customer> customersWithProduct = new ArrayList<>();

        for (Customer customer : customers) {
            List<Order> orders = customer.getOrders();
            for (Order order : orders) {
                List<OrderDetail> orderDetails = order.getOrderDetail();
                for (OrderDetail orderDetail : orderDetails) {
                    Product product = orderDetail.getProduct();
                    if (product != null && product.getId().equals(productId)) {
                        if (!customersWithProduct.contains(customer)) {
                            customersWithProduct.add(customer);
                        }
                    }
                }
            }
        }
        return customersWithProduct;
    }

    // --------------------------- EXTRA METHODS -----------------------------//
    public void validateCustomer(CustomerDTO customerDTO) {

        if (customerDTO == null) {
            throw new IllegalArgumentException("CategoryDTO must not be null");
        }

        String customerDTOName = customerDTO.getFirstName() + " " + customerDTO.getLastName();

        if (customerDTOName == null || customerDTOName.trim().isEmpty()) {
            throw new IllegalArgumentException("Customer name must not be null or empty");
        }

        Optional<Customer> existingEmail = customerRepository.findByEmail(customerDTO.getEmail());
        if (existingEmail.isPresent()) {
            throw new IllegalArgumentException("Customer with mail '" + existingEmail + "' already exists");
        }

        Optional<Customer> existingPhone = customerRepository.findByPhone(customerDTO.getPhone());
        if (existingPhone.isPresent()) {
            throw new IllegalArgumentException("Customer with phone '" + existingPhone + "' already exists");
        }

    }

    public void validateExistingCustomer(Long id, CustomerDTO customerDTO) {

        if (id == null) {
            throw new IllegalArgumentException("Customer ID cannot be null.");
        }

        if (customerDTO == null) {
            throw new IllegalArgumentException("Customer data cannot be null.");
        }

        if (!customerRepository.existsById(id)) {
            throw new EntityNotFoundException("Customer with id " + id + " not found.");
        }

        if (customerDTO.getFirstName() == null || customerDTO.getFirstName().isBlank()) {
            throw new IllegalArgumentException("First name cannot be empty.");
        }

        if (customerDTO.getLastName() == null || customerDTO.getLastName().isBlank()) {
            throw new IllegalArgumentException("Last name cannot be empty.");
        }

        if (customerDTO.getEmail() == null || !customerDTO.getEmail().matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            throw new IllegalArgumentException("Invalid email format.");
        }

        if (customerDTO.getPhone() == null || !customerDTO.getPhone().matches("^[0-9\\-\\+]{9,15}$")) {
            throw new IllegalArgumentException("Invalid phone number format.");
        }

        Optional<Customer> customerByEmail = customerRepository.findByEmail(customerDTO.getEmail());
        if (customerByEmail.isPresent() && !customerByEmail.get().getId().equals(id)) {
            throw new IllegalArgumentException("Email is already used by another customer.");
        }

        Optional<Customer> customerByPhone = customerRepository.findByPhone(customerDTO.getPhone());
        if (customerByPhone.isPresent() && !customerByPhone.get().getId().equals(id)) {
            throw new IllegalArgumentException("Phone number is already used by another customer.");
        }

    }

    public CustomerDTO toCustomerDTO(Customer customer) {
        return new CustomerDTO(
                customer.getId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getRegistrationDate(),
                customer.isActive());
    }

}
