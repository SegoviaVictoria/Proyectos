package com.civica.tiendaOnline.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ProductDTO {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private int stock;
    private LocalDateTime updateDate;
    private String categoryName;

    public ProductDTO(Long id, String name, String description, BigDecimal price, int stock, LocalDateTime updateDate,
            String categoryName) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.updateDate = updateDate;
        this.categoryName = categoryName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return "ProductDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                ", updateDate=" + updateDate +
                ", categoryName='" + categoryName + '\'' +
                '}';
    }

}
