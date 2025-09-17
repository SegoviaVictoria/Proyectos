package com.civica.tiendaOnline.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.civica.tiendaOnline.dto.OrderDetailDTO;
import com.civica.tiendaOnline.model.OrderDetail;
import com.civica.tiendaOnline.service.OrderDetailService;

@RestController
@RequestMapping("/api/orderDetails")
public class OrderDetailController {

    @Autowired
    private OrderDetailService orderDetailService;

    @GetMapping
    public List<OrderDetailDTO> getAllDetailDTOs() {
        return orderDetailService.getAllOrderDetailsDTOs();
    }

    @GetMapping("/{id}")
    public OrderDetailDTO getOrderDetailDTOById(@PathVariable Long id) {
        return orderDetailService.getOrderDetailDTOById(id);
    }

    @PostMapping
    public OrderDetailDTO createOrderDetail(@RequestBody OrderDetailDTO orderDetailDTO) {
        OrderDetail saved = orderDetailService.toOrderDetail(orderDetailDTO);
        return orderDetailService.toOrderDetailDTO(saved);
    }

    @PutMapping("/{id}")
    public OrderDetailDTO updateOrderDetail(@PathVariable Long id, @RequestBody OrderDetail orderDetail) {
        OrderDetail updated = orderDetailService.updateOrderDetail(id, orderDetail);
        return orderDetailService.toOrderDetailDTO(updated);
    }

    @DeleteMapping("/{id}")
    public void deleteOrderDetail(@PathVariable Long id) {
        orderDetailService.deleteOrderDetailById(id);
    }
}
