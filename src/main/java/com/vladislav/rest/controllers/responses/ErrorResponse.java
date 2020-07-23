package com.vladislav.rest.controllers.responses;

import lombok.Value;
import org.springframework.http.HttpStatus;

@Value
public class ErrorResponse {

    Integer status;
    String message;

}
