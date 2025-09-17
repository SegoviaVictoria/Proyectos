package com.civica.tiendaOnline.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.civica.tiendaOnline.model.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

    boolean existsByProductId(Long productId);

}
