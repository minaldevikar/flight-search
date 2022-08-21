package com.flyhigh.flightsearch.util;

import org.springframework.stereotype.Component;

/**
 * Created by Admin on 8/21/2022.
 */
@Component
public class ErrorResponse {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
