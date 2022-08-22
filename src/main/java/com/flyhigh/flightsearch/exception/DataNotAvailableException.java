package com.flyhigh.flightsearch.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Admin on 8/22/2022.
 */
@ResponseStatus(value= HttpStatus.BAD_REQUEST)
public class DataNotAvailableException extends RuntimeException{

    public DataNotAvailableException() {
        super("Data not available");
    }
}
