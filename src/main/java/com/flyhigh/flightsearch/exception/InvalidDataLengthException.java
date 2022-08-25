package com.flyhigh.flightsearch.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Admin on 8/22/2022.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidDataLengthException extends RuntimeException {

    public InvalidDataLengthException() {
        super("3 Letter Origin and Destination airport code required");
    }

}
