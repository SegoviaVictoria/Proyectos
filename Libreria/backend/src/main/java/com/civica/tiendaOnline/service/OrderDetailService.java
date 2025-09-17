package com.civica.tiendaOnline.service;

import java.util.List;

import com.civica.tiendaOnline.dto.OrderDetailDTO;
import com.civica.tiendaOnline.model.OrderDetail;

public interface OrderDetailService {

    OrderDetail createOrderDetail(OrderDetail category);

    OrderDetail updateOrderDetail(Long id, OrderDetail orderDetail);

    void deleteOrderDetailById(Long id);

    List<OrderDetailDTO> getAllOrderDetailsDTOs();

    OrderDetailDTO getOrderDetailDTOById(Long id);

    OrderDetailDTO toOrderDetailDTO(OrderDetail orderDetail);

    OrderDetail toOrderDetail(OrderDetailDTO orderDetailDTO);

}
