package com.marvis.primeloan.exception;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException( String message) {
        super(message);
    }
}
