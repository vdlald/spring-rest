package com.vladislav.rest.controllers.advices;

import com.vladislav.rest.controllers.responses.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

@ControllerAdvice
public class ConstraintViolationAdvice {

    @ResponseBody
    @ExceptionHandler(TransactionSystemException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse resourceNotFoundHandler(TransactionSystemException exception) {
        final ConstraintViolationException cause = (ConstraintViolationException) exception.getRootCause();
        assert cause != null;
        final String message = cause.getConstraintViolations().stream()
                .map(constraintViolation -> String.format("%s: %s", constraintViolation.getPropertyPath(),
                        constraintViolation.getMessage()))
                .collect(Collectors.joining("\n"));
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), message);
    }

}
