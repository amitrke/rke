package org.roorkee.rkerestapi.controller;

import org.roorkee.rkerestapi.util.ErrorResource;
import org.roorkee.rkerestapi.util.RkeException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class MyExceptionHandler extends ResponseEntityExceptionHandler{

    @ExceptionHandler({RkeException.class})
    protected ResponseEntity<Object> handleInvalidRequest(RkeException e, WebRequest request){
        ErrorResource error = new ErrorResource("InvalidRequest", e.getMessage());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return handleExceptionInternal(e, error, headers, HttpStatus.UNPROCESSABLE_ENTITY, request);
    }
    
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e, 
    		HttpHeaders headers, HttpStatus status, WebRequest request){
    	ErrorResource error = new ErrorResource("InvalidRequest", e.getMessage());
    	return handleExceptionInternal(e, error, headers, HttpStatus.UNPROCESSABLE_ENTITY, request);
    }
    
    
}
