package com.example.claudecodeproject.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "price")
public class Price {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal salePrice;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal listPrice;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime modifiedAt;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public BigDecimal getSalePrice() { return salePrice; }
    public void setSalePrice(BigDecimal salePrice) { this.salePrice = salePrice; }

    public BigDecimal getListPrice() { return listPrice; }
    public void setListPrice(BigDecimal listPrice) { this.listPrice = listPrice; }

    public LocalDateTime getCreatedAt() { return createdAt; }

    public LocalDateTime getModifiedAt() { return modifiedAt; }
}
