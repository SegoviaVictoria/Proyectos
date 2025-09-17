package com.civica.tiendaOnline.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.civica.tiendaOnline.model.Category;
import com.civica.tiendaOnline.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByLastName(String lastName);

    List<Customer> findByOrdersOrderDetailProductId(Long productId);

    Optional<Customer> findByEmail(String email);

    Optional<Customer> findByPhone(String phone);

}
