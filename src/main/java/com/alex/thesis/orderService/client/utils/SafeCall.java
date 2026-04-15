package com.alex.thesis.orderService.client.utils;

import java.util.function.Function;
import java.util.function.Supplier;

public final class SafeCall {
    private SafeCall() {}

    public static <T> CallResult<T> run(
        Supplier<T> method,
        Function<Throwable, Throwable> errorHandler
    ){
        try {
            return CallResult.ok(method.get());
        } catch (Throwable t) {
            return CallResult.fail(errorHandler.apply(t));
        }
    }

    public static <T> CallResult<T> run(Supplier<T> method){
        return run(method, t -> t);
    }
}
