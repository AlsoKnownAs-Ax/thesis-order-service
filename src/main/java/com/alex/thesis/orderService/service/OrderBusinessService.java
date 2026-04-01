package com.alex.thesis.orderService.service;

import org.springframework.stereotype.Service;

import com.alex.thesis.orderService.entity.Order;
import com.alex.thesis.orderService.repository.OrderRepository;

@Service
public class OrderBusinessService {
    
    private final OrderRepository orderRepository;

    public OrderBusinessService(OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }

    public Order createOrder(String userId, String productId, int quantity){
        double unitPrice = 10.0; // TODO: fetch it from product service
        double totalPrice = unitPrice * quantity;

        Order order = new Order();
        order.setUserId(userId);
        order.setProductId(productId);
        order.setQuantity(quantity);
        order.setUnitPrice(unitPrice);
        order.setTotalPrice(totalPrice);
        order.setStatus("CREATED");

        return orderRepository.save(order);
    }

    public Order getOrderById(Long orderId){
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }
}
