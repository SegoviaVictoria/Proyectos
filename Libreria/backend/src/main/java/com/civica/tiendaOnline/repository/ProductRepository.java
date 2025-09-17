package com.civica.tiendaOnline.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.civica.tiendaOnline.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByName(String name);

}
