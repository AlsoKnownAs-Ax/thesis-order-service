package com.alex.thesis.orderService.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alex.thesis.orderService.client.services.DownstreamProductService;
import com.alex.thesis.orderService.client.services.DownstreamUserService;
import com.alex.thesis.proto.product.v1.CheckAvailabilityResponse;
import com.alex.thesis.proto.product.v1.GetProductByIdResponse;
import com.alex.thesis.proto.product.v1.Product;
import com.alex.thesis.proto.user.v1.ValidateUserResponse;

@Service
public class DownstreamFacade implements IDownstreamFacade {

    private final Logger log = LoggerFactory.getLogger(DownstreamFacade.class);

    private final DownstreamUserService userService;
    private final DownstreamProductService productService;

    public DownstreamFacade(
        DownstreamUserService userService,
        DownstreamProductService productService
    ){
        this.userService = userService;
        this.productService = productService;
    }

    @Override
    public void validateActiveUser(String userId) {
        ValidateUserResponse response = userService.validateUser(userId);
        log.info("Validate user Response: " + response.toString());
        if(!response.getExists()){
            throw new IllegalArgumentException("User does not exist:" + userId);
        }
        if(!response.getIsActive()){
            throw new IllegalArgumentException("User is not active: " + userId);
        }
    }

    @Override
    public double getUnitPriceOrThrow(String productId) {
        GetProductByIdResponse response = productService.getProductById(productId);
        if(!response.hasProduct()){
            throw new IllegalArgumentException("Product not found: " + productId);
        }
        Product product = response.getProduct();
        return product.getPrice();
    }

    @Override
    public void ensureProductAvailability(String productId, int quantity) {
        CheckAvailabilityResponse response = productService.checkAvailability(productId, quantity);
        if(!response.getIsAvailable()){
            throw new IllegalArgumentException(
                "Insufficient stock for product" + productId + ", available=" + response.getAvailableQuantity()
            );
        }
    }
    
}
