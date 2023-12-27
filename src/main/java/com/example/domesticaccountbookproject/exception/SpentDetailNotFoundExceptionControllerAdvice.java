package com.example.domesticaccountbookproject.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class SpentDetailNotFoundExceptionControllerAdvice {

    @ResponseBody
    @ExceptionHandler(SpentDetailNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String spentDetailNotFoundHandler(SpentDetailNotFoundException e) {
        return e.getMessage();
    }

}
