package com.civica.tiendaOnline.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.civica.tiendaOnline.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
