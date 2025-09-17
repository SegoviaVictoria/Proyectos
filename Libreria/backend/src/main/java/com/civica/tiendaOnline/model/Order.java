package com.civica.tiendaOnline.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "oderDate", nullable = false)
    private LocalDateTime orderDate;

    @Column(name = "is_shipped", nullable = false)
    private boolean isShipped;

    @Column(name = "total", nullable = false)
    private BigDecimal total;

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER, cascade = jakarta.persistence.CascadeType.ALL)
    @JsonManagedReference
    private List<OrderDetail> orderDetail = new ArrayList<>();

    @ManyToOne
    private Customer customer;

    public Order() {
    }

    public Order(long id, LocalDateTime orderDate, boolean isShipped, BigDecimal total, List<OrderDetail> orderDetail,
            Customer customer) {
        this.id = id;
        this.customer = customer;
        this.orderDate = orderDate;
        this.isShipped = isShipped;
        this.total = total;
        this.orderDetail = new ArrayList<>(orderDetail);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public void setShipped(boolean shipped) {
        isShipped = shipped;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public List<OrderDetail> getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(List<OrderDetail> orderDetail) {
        this.orderDetail = orderDetail;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderDate=" + orderDate +
                ", isShipped=" + isShipped +
                ", total=" + total +
                ", orderDetail=" + orderDetail +
                ", customer=" + customer +
                '}';
    }

    public void addOrderDetail(OrderDetail orderDetail) {
        this.orderDetail.add(orderDetail);
        orderDetail.setOrder(this);
    }

}