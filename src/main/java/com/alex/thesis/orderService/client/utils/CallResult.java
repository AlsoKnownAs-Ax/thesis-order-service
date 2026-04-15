package com.alex.thesis.orderService.client.utils;

public record CallResult<T>(T Data, Throwable error) {
    
    public static <T> CallResult<T> ok(T data){
        return new CallResult<>(data, null);
    }

    public static <T> CallResult<T> fail(Throwable error){
        return new CallResult<>(null, error);
    }

    public boolean isSuccess(){
        return error == null;
    }
}
