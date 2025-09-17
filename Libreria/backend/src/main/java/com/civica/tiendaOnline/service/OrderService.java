package com.civica.tiendaOnline.service;

import java.util.List;

import com.civica.tiendaOnline.dto.OrderDTO;
import com.civica.tiendaOnline.model.Order;

public interface OrderService {

    OrderDTO getOrderDTOById(Long id);

    OrderDTO toOrderDTO(Order order);

    OrderDTO createOrder(OrderDTO orderDTO);

    OrderDTO updateOrder(Long id, OrderDTO orderDTO);

    void deleteOrderById(Long id);

    List<OrderDTO> getAllOrdersDTOs();

    // List<OrderDTO> getAllOrdersDTOsByCustomerId(Long customerId);
}
