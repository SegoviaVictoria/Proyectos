package com.civica.tiendaOnline.service.serviceImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.civica.tiendaOnline.dto.OrderDTO;
import com.civica.tiendaOnline.dto.OrderDetailDTO;
import com.civica.tiendaOnline.model.Customer;
import com.civica.tiendaOnline.model.Order;
import com.civica.tiendaOnline.model.OrderDetail;
import com.civica.tiendaOnline.model.Product;
import com.civica.tiendaOnline.repository.CustomerRepository;
import com.civica.tiendaOnline.repository.OrderRepository;
import com.civica.tiendaOnline.repository.ProductRepository;
import com.civica.tiendaOnline.service.OrderService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<OrderDTO> getAllOrdersDTOs() {
        return orderRepository.findAll().stream()
                .map(this::toOrderDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public OrderDTO getOrderDTOById(Long id) {
        return orderRepository.findById(id)
                .map(this::toOrderDTO)
                .orElse(null);
    }

    @Override
    @Transactional
    public OrderDTO createOrder(OrderDTO orderDTO) {
        validateOrderDTO(orderDTO, false);

        Order order = toOrder(orderDTO);

        BigDecimal total = BigDecimal.ZERO;
        if (order.getOrderDetail() != null) {
            for (OrderDetail detail : order.getOrderDetail()) {
                Product product = productRepository.findById(detail.getProduct().getId())
                        .orElseThrow(() -> new EntityNotFoundException("Product with id: " + detail.getProduct().getId() + " not found."));

                BigDecimal quantity = BigDecimal.valueOf(detail.getQuantity());
                BigDecimal unitPrice = product.getPrice();

                detail.setPrice(unitPrice);
                detail.setOrder(order);
                total = total.add(unitPrice.multiply(quantity));
            }
        }

        order.setTotal(total);
        Order savedOrder = orderRepository.save(order);

        return toOrderDTO(savedOrder);
    }

    @Override
    @Transactional
    public OrderDTO updateOrder(Long id, OrderDTO orderDTO) {
        validateOrderDTO(orderDTO, true);

        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order with ID " + id + " not found."));

        existingOrder.setOrderDate(orderDTO.getOrderDate());
        existingOrder.setShipped(orderDTO.isShipped());

        if (orderDTO.getCustomerId() != null && orderDTO.getCustomerId() != 0L) {
            Customer customer = customerRepository.findById(orderDTO.getCustomerId())
                    .orElseThrow(() -> new EntityNotFoundException("Customer not found with ID: " + orderDTO.getCustomerId()));
            existingOrder.setCustomer(customer);
        } else {
            existingOrder.setCustomer(null);
        }

        if (orderDTO.getDetails() != null) {
            List<OrderDetail> updatedDetails = new ArrayList<>();
            for (OrderDetailDTO detailDTO : orderDTO.getDetails()) {
                Product product = productRepository.findById(detailDTO.getProductId())
                        .orElseThrow(() -> new EntityNotFoundException("Product not found with ID: " + detailDTO.getProductId()));

                Optional<OrderDetail> existingDetailOpt = existingOrder.getOrderDetail().stream()
                        .filter(d -> d.getId() == detailDTO.getId())
                        .findFirst();

                OrderDetail detail;
                if (existingDetailOpt.isPresent()) {
                    detail = existingDetailOpt.get();
                    detail.setQuantity(detailDTO.getQuantity());
                    detail.setPrice(product.getPrice());
                    detail.setProduct(product);
                } else {
                    detail = new OrderDetail();
                    detail.setProduct(product);
                    detail.setQuantity(detailDTO.getQuantity());
                    detail.setPrice(product.getPrice());
                    detail.setOrder(existingOrder);
                }
                updatedDetails.add(detail);
            }
            existingOrder.setOrderDetail(mergeOrderDetails(updatedDetails));
        }

        recalculateTotal(existingOrder);

        Order savedOrder = orderRepository.save(existingOrder);
        return toOrderDTO(savedOrder);
    }

    @Override
    public void deleteOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order with ID " + id + " not found."));

        if (order.getOrderDetail() != null && !order.getOrderDetail().isEmpty()) {
            throw new IllegalStateException("Cannot delete Order with associated OrderDetails.");
        }

        orderRepository.deleteById(id);
    }

    // ------------------- EXTRA METHODS ------------------- //
    private void validateOrderDTO(OrderDTO orderDTO, boolean isUpdate) {
        if (orderDTO == null) {
            throw new IllegalArgumentException("OrderDTO cannot be null.");
        }
        if (isUpdate && (orderDTO.getId() == 0 || orderDTO.getId() == null)) {
            throw new IllegalArgumentException("Order ID must be provided for update.");
        }
        if (orderDTO.getCustomerId() != null && orderDTO.getCustomerId() != 0L) {
            boolean exists = customerRepository.existsById(orderDTO.getCustomerId());
            if (!exists) {
                throw new EntityNotFoundException("Customer with ID " + orderDTO.getCustomerId() + " does not exist.");
            }
        }
        if (orderDTO.getDetails() != null) {
            for (OrderDetailDTO detailDTO : orderDTO.getDetails()) {
                if (detailDTO.getProductId() == 0L) {
                    throw new IllegalArgumentException("Product ID in order details must be valid.");
                }
                boolean productExists = productRepository.existsById(detailDTO.getProductId());
                if (!productExists) {
                    throw new EntityNotFoundException("Product with ID " + detailDTO.getProductId() + " does not exist.");
                }
                if (detailDTO.getQuantity() <= 0) {
                    throw new IllegalArgumentException("Quantity must be greater than zero.");
                }
            }
        }
    }

    private List<OrderDetail> mergeOrderDetails(List<OrderDetail> details) {
        Map<Long, OrderDetail> detailMap = new HashMap<>();
        for (OrderDetail detail : details) {
            Long productId = detail.getProduct().getId();
            if (detailMap.containsKey(productId)) {
                OrderDetail existing = detailMap.get(productId);
                existing.setQuantity(existing.getQuantity() + detail.getQuantity());
            } else {
                detailMap.put(productId, detail);
            }
        }
        return new ArrayList<>(detailMap.values());
    }

    private void recalculateTotal(Order order) {
        BigDecimal total = BigDecimal.ZERO;
        if (order.getOrderDetail() != null) {
            for (OrderDetail detail : order.getOrderDetail()) {
                total = total.add(detail.getPrice().multiply(BigDecimal.valueOf(detail.getQuantity())));
            }
        }
        order.setTotal(total);
    }

    public OrderDTO toOrderDTO(Order order) {
        long customerId = 0L;
        String customerName = "Unassigned Customer";

        if (order.getCustomer() != null) {
            customerId = order.getCustomer().getId();

            String firstName = Optional.ofNullable(order.getCustomer().getFirstName()).orElse("");
            String lastName = Optional.ofNullable(order.getCustomer().getLastName()).orElse("");
            customerName = (firstName + " " + lastName).trim();

            if (customerName.isBlank()) {
                customerName = "Customer without name";
            }
        }

        List<OrderDetailDTO> details = order.getOrderDetail() != null
                ? order.getOrderDetail().stream()
                        .map(detail -> new OrderDetailDTO(
                        detail.getId(),
                        detail.getProduct() != null ? detail.getProduct().getId() : 0L,
                        detail.getProduct() != null ? detail.getProduct().getName() : "Product not available",
                        detail.getQuantity(),
                        detail.getPrice()))
                        .collect(Collectors.toList())
                : new ArrayList<>();

        return new OrderDTO(
                order.getId(),
                order.getOrderDate(),
                order.isShipped(),
                order.getTotal(),
                customerName,
                customerId,
                details);
    }

    public Order toOrder(OrderDTO orderDTO) {
        Order order = new Order();

        order.setId(orderDTO.getId());
        order.setOrderDate(orderDTO.getOrderDate());
        order.setShipped(orderDTO.isShipped());
        order.setTotal(orderDTO.getTotal());

        if (orderDTO.getCustomerId() != null && orderDTO.getCustomerId() != 0L) {
            Customer customer = customerRepository.findById(orderDTO.getCustomerId())
                    .orElseThrow(() -> new EntityNotFoundException("Customer not found with ID: " + orderDTO.getCustomerId()));
            order.setCustomer(customer);
        }

        List<OrderDetail> details = new ArrayList<>();
        if (orderDTO.getDetails() != null) {
            for (OrderDetailDTO d : orderDTO.getDetails()) {
                Product product = productRepository.findById(d.getProductId())
                        .orElseThrow(() -> new EntityNotFoundException("Product not found with ID: " + d.getProductId()));

                OrderDetail detail = new OrderDetail();
                detail.setProduct(product);
                detail.setQuantity(d.getQuantity());
                detail.setPrice(product.getPrice());
                detail.setOrder(order);

                details.add(detail);
            }
        }
        order.setOrderDetail(mergeOrderDetails(details));

        return order;
    }
}
