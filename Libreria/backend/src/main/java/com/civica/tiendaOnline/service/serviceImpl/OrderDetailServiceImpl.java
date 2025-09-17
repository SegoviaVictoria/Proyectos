package com.civica.tiendaOnline.service.serviceImpl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.civica.tiendaOnline.dto.OrderDetailDTO;
import com.civica.tiendaOnline.model.Order;
import com.civica.tiendaOnline.model.OrderDetail;
import com.civica.tiendaOnline.model.Product;
import com.civica.tiendaOnline.repository.OrderDetailRepository;
import com.civica.tiendaOnline.repository.OrderRepository;
import com.civica.tiendaOnline.repository.ProductRepository;
import com.civica.tiendaOnline.service.OrderDetailService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<OrderDetailDTO> getAllOrderDetailsDTOs() {
        return orderDetailRepository.findAll().stream()
                .map(this::toOrderDetailDTO)
                .toList();
    }

    @Override
    public OrderDetailDTO getOrderDetailDTOById(Long id) {
        return orderDetailRepository.findById(id)
                .map(this::toOrderDetailDTO)
                .orElse(null);
    }

    @Override
    public OrderDetail createOrderDetail(OrderDetail orderDetail) {
        validateOrderDetailProduct(orderDetail);

        Product product = productRepository.findById(orderDetail.getProduct().getId())
                .orElseThrow(() -> new RuntimeException("Product with id: " + orderDetail.getProduct().getId() + " not found."));

        orderDetail.setProduct(product);
        orderDetail.setPrice(product.getPrice());

        OrderDetail savedOrderDetail = orderDetailRepository.save(orderDetail);

        recalculateOrderTotalIfOrderExists(savedOrderDetail.getOrder());

        return savedOrderDetail;
    }

    @Override
    public OrderDetail updateOrderDetail(Long id, OrderDetail orderDetail) {
        OrderDetail existingOrderDetail = orderDetailRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("OrderDetail with ID " + id + " not found."));

        updateOrderDetailFields(existingOrderDetail, orderDetail);

        OrderDetail updatedOrderDetail = orderDetailRepository.save(existingOrderDetail);

        recalculateOrderTotalIfOrderExists(updatedOrderDetail.getOrder());

        return updatedOrderDetail;
    }

    @Override
    public void deleteOrderDetailById(Long id) {
        OrderDetail orderDetail = orderDetailRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("OrderDetail with ID " + id + " not found."));

        Order order = orderDetail.getOrder();

        orderDetailRepository.deleteById(id);

        if (order != null) {
            recalculateOrderTotalIfOrderExists(order);
        }
    }

    // ---------------------- EXTRA METHODS ----------------------------- //
    private void validateOrderDetailProduct(OrderDetail orderDetail) {
        if (orderDetail.getProduct() == null || orderDetail.getProduct().getId() == null || orderDetail.getProduct().getId() == 0) {
            throw new IllegalArgumentException("El producto es obligatorio y debe tener un ID válido");
        }
    }

    private void updateOrderDetailFields(OrderDetail existing, OrderDetail updated) {
        existing.setQuantity(updated.getQuantity());
        existing.setPrice(updated.getPrice());

        if (updated.getProduct() != null && updated.getProduct().getId() != null) {
            Long newProductId = updated.getProduct().getId();
            Long currentProductId = existing.getProduct() != null ? existing.getProduct().getId() : null;

            if (!newProductId.equals(currentProductId)) {
                Product newProduct = productRepository.findById(newProductId)
                        .orElseThrow(() -> new EntityNotFoundException("Product with ID " + newProductId + " not found."));
                existing.setProduct(newProduct);
            }
        }
    }

    private void recalculateOrderTotalIfOrderExists(Order order) {
        if (order == null) {
            return;
        }
        BigDecimal total = order.getOrderDetail().stream()
                .map(od -> od.getPrice().multiply(BigDecimal.valueOf(od.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setTotal(total);
        orderRepository.save(order);
    }

    @Override
    public OrderDetailDTO toOrderDetailDTO(OrderDetail orderDetail) {
        String productName = orderDetail.getProduct() != null ? orderDetail.getProduct().getName() : "Producto no asignado";
        Long productId = orderDetail.getProduct() != null ? orderDetail.getProduct().getId() : 0L;

        return new OrderDetailDTO(
                orderDetail.getId(),
                productId,
                productName,
                orderDetail.getQuantity(),
                orderDetail.getPrice());
    }

    @Override
    public OrderDetail toOrderDetail(OrderDetailDTO orderDetailDTO) {
        if (orderDetailDTO == null) {
            throw new IllegalArgumentException("OrderDetailDTO must not be null.");
        }

        if (orderDetailDTO.getProductId() <= 0) {
            throw new IllegalArgumentException("Invalid or missing productId in OrderDetailDTO.");
        }

        Product product = productRepository.findById(orderDetailDTO.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("Product with ID " + orderDetailDTO.getProductId() + " not found."));

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setId(orderDetailDTO.getId());
        orderDetail.setProduct(product);
        orderDetail.setQuantity(orderDetailDTO.getQuantity());
        orderDetail.setPrice(orderDetailDTO.getPrice());

        return orderDetail;
    }

}
