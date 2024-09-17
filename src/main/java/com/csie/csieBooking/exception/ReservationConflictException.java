package com.csie.csieBooking.exception;

import org.springframework.context.annotation.Bean;


public class ReservationConflictException extends RuntimeException {
    public ReservationConflictException(String message) {
        super(message);
    }
}
