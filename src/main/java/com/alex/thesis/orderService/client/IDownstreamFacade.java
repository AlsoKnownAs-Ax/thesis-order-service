package com.alex.thesis.orderService.client;

public interface IDownstreamFacade {
    void validateActiveUser(String userId);
    double getUnitPriceOrThrow(String productId);
    void ensureProductAvailability(String productId, int quantity);
}
