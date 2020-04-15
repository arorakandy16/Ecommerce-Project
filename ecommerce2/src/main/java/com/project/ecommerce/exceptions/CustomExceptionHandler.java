package com.project.ecommerce.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
//because i want this custom exception to apply to all the controllers like sellerController and Customercontroller
@RestController  //bcz it is returning response
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAlException(Exception ex, WebRequest request) {

        //we want to create new instance of our specific bean (ExceptionResponse) and return exception resoponse back
        ExceptionResponse exceptionRespnse = new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));

        return new ResponseEntity(exceptionRespnse, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    //
    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex, WebRequest request) {

        //we want to create new instance of our specific bean (ExceptionResponse) and return exception resoponse back
        ExceptionResponse exceptionRespnse = new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));

        return new ResponseEntity(exceptionRespnse, HttpStatus.NOT_FOUND);

    }

    //i want to give consumer the information what exactly went wrong during validation and that can only be told to the user using message(using binding result)
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        //ExceptionResponse exceptionRespnse=new ExceptionResponse(new Date(),ex.getMessage(),ex.getBindingResult().toString());
        ExceptionResponse exceptionRespnse = new ExceptionResponse(new Date(), "Validation failed..", ex.getBindingResult().toString());

        return new ResponseEntity(exceptionRespnse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConfirmPasswordException.class)
    public final ResponseEntity<Object> handleConfirmPasswordException(ConfirmPasswordException ex, WebRequest request) {

        //we want to create new instance of our specific bean (ExceptionResponse) and return exception resoponse back
        ExceptionResponse exceptionRespnse = new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));

        return new ResponseEntity(exceptionRespnse, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(UserAlreadyRegisteredException.class)
    public final ResponseEntity<Object> handleUserAlreadyRegisteredException (UserAlreadyRegisteredException ex, WebRequest request) {

        //we want to create new instance of our specific bean (ExceptionResponse) and return exception resoponse back
        ExceptionResponse exceptionRespnse = new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));

        return new ResponseEntity(exceptionRespnse, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(ServerSideException.class)
    public final ResponseEntity<Object> handleAllServerSideException (ServerSideException ex, WebRequest request) {

        //we want to create new instance of our specific bean (ExceptionResponse) and return exception resoponse back
        ExceptionResponse exceptionRespnse = new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));

        return new ResponseEntity(exceptionRespnse, HttpStatus.INTERNAL_SERVER_ERROR);

    }
}