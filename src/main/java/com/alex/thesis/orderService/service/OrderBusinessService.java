package com.alex.thesis.orderService.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alex.thesis.orderService.client.DownstreamFacade;
import com.alex.thesis.orderService.entity.Order;
import com.alex.thesis.orderService.repository.OrderRepository;

@Service
public class OrderBusinessService {

    private static final Logger log = LoggerFactory.getLogger(OrderBusinessService.class);

    private final OrderRepository orderRepository;
    private final DownstreamFacade downstreamService;

    public OrderBusinessService(OrderRepository orderRepository, DownstreamFacade downstreamFacade){
        this.orderRepository = orderRepository;
        this.downstreamService = downstreamFacade;
    }

    public Order createOrder(String userId, String productId, int quantity){
        log.info("createOrder start userId={}, productId={}, quantity={}", userId, productId, quantity);
        
        //Guards for the order
        downstreamService.validateActiveUser(userId);
        log.info("user validated userId={}", userId);
        downstreamService.ensureProductAvailability(productId, quantity);
        log.info("product availability ok productId={}, quantity={}", productId, quantity);

        double unitPrice = downstreamService.getUnitPriceOrThrow(productId);
        double totalPrice = unitPrice * quantity;

        log.info("pricing resolved productId={}, unitPrice={}, totalPrice={}", productId, unitPrice, totalPrice);

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
