package com.alex.thesis.orderService.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue
    private Long id;
    
    private String userId;
    private String productId;
    private Integer quantity;
    private Double unitPrice;
    private Double totalPrice;
    private String status;
    private LocalDateTime createdAt;
}
