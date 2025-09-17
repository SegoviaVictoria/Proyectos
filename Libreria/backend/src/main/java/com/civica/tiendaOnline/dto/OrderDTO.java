package com.civica.tiendaOnline.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderDTO {

    private Long id;
    private LocalDateTime orderDate;
    private boolean isShipped;
    private BigDecimal total;
    private Long customerId;
    private String customerName;
    private List<OrderDetailDTO> details;

    public OrderDTO() {
    }

    public OrderDTO(LocalDateTime orderDate, boolean isShipped, Long customerId, List<OrderDetailDTO> details) {
        this.orderDate = orderDate;
        this.isShipped = isShipped;
        this.customerId = customerId;
        this.details = details;
    }

    public OrderDTO(Long id, LocalDateTime orderDate, boolean isShipped, BigDecimal total, String customerName,
            Long customerId,
            List<OrderDetailDTO> details) {
        this.id = id;
        this.orderDate = orderDate;
        this.isShipped = isShipped;
        this.total = total;
        this.customerId = customerId;
        this.customerName = customerName;
        this.details = details;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public boolean isShipped() {
        return isShipped;
    }

    public void setShipped(boolean isShipped) {
        this.isShipped = isShipped;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public List<OrderDetailDTO> getDetails() {
        return details;
    }

    public void setDetails(List<OrderDetailDTO> details) {
        this.details = details;
    }

}
