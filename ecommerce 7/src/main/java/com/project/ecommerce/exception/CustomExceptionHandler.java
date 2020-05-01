package com.project.ecommerce.exception;

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

//Because I want this custom exception to apply to all the controllers

@RestController  //bcz it is returning response
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAlException
            (Exception ex,
             WebRequest request) {


        //we want to create new instance of our specific bean
        // (ExceptionResponse) and return exception response back
        ExceptionResponse exceptionResponse
                = new ExceptionResponse(new Date(), ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);

    }


    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<Object> handleUserNotFoundException
            (UserNotFoundException ex, WebRequest request) {


        //we want to create new instance of our specific bean
        // (ExceptionResponse) and return exception response back
        ExceptionResponse exceptionResponse
                = new ExceptionResponse
                (new Date(), ex.getMessage(),
                        request.getDescription(false));

        return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);

    }


    //I want to give consumer the information what exactly went wrong
    // during validation and that can only be told to the user using message
    // (using binding result)

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid
            (MethodArgumentNotValidException ex,
             HttpHeaders headers,
             HttpStatus status,
             WebRequest request) {


        //ExceptionResponse exceptionResponse=new ExceptionResponse
        // (new Date(),
        // ex.getMessage(),
        // ex.getBindingResult()
        // .toString());

        ExceptionResponse exceptionResponse
                = new ExceptionResponse
                (new Date(), "Validation failed..",
                        ex.getBindingResult().toString());

        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(ConfirmPasswordException.class)
    public final ResponseEntity<Object> handleConfirmPasswordException
            (ConfirmPasswordException ex,
             WebRequest request) {


        //we want to create new instance of our specific bean
        // (ExceptionResponse) and return exception response back

        ExceptionResponse exceptionResponse
                = new ExceptionResponse
                (new Date(), ex.getMessage(),
                        request.getDescription(false));

        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);

    }


    @ExceptionHandler(UserAlreadyRegisteredException.class)
    public final ResponseEntity<Object> handleUserAlreadyRegisteredException
            (UserAlreadyRegisteredException ex,
             WebRequest request) {

        //we want to create new instance of our specific bean
        // (ExceptionResponse) and return exception response back
        ExceptionResponse exceptionResponse
                = new ExceptionResponse
                (new Date(),
                        ex.getMessage(),
                        request.getDescription(false));

        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(ServerSideException.class)
    public final ResponseEntity<Object> handleAllServerSideException
            (ServerSideException ex,
             WebRequest request) {

        //we want to create new instance of our specific bean
        // (ExceptionResponse) and return exception response back
        ExceptionResponse exceptionResponse
                = new ExceptionResponse
                (new Date(),
                        ex.getMessage(),
                        request.getDescription(false));

        return new ResponseEntity(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);

    }


    @ExceptionHandler(ProductNotFoundException.class)
    public final ResponseEntity<Object> handleAllProductNotFoundException
            (ProductNotFoundException ex,
             WebRequest request) {

        //we want to create new instance of our specific bean
        // (ExceptionResponse) and return exception response back
        ExceptionResponse exceptionResponse
                = new ExceptionResponse
                (new Date(),
                        ex.getMessage(),
                        request.getDescription(false));

        return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);

    }


    @ExceptionHandler(CategoryAlreadyRegistered.class)
    public final ResponseEntity<Object> handleAllCategoryAlreadyRegisteredException
            (CategoryAlreadyRegistered ex,
             WebRequest request) {

        //we want to create new instance of our specific bean
        // (ExceptionResponse) and return exception response back
        ExceptionResponse exceptionResponse
                = new ExceptionResponse
                (new Date(),
                        ex.getMessage(),
                        request.getDescription(false));

        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(InvalidCategoryOrFieldIdException.class)
    public final ResponseEntity<Object> handleAllInvalidCategoryOrFieldIdException
            (InvalidCategoryOrFieldIdException ex,
             WebRequest request) {

        //we want to create new instance of our specific bean
        // (ExceptionResponse) and return exception response back
        ExceptionResponse exceptionResponse
                = new ExceptionResponse
                (new Date(),
                        ex.getMessage(),
                        request.getDescription(false));

        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);

    }


    @ExceptionHandler(ValidationException.class)
        public final ResponseEntity<Object> handleAllValidationException
            (ValidationException ex,
             WebRequest request) {

        //we want to create new instance of our specific bean
        // (ExceptionResponse) and return exception response back
        ExceptionResponse exceptionResponse
                = new ExceptionResponse
                (new Date(),
                        ex.getMessage(),
                        request.getDescription(false));

        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);

    }



    @ExceptionHandler(Message.class)
    public final ResponseEntity<Object> handleAllMessage
            (Message ex,
             WebRequest request) {

        //we want to create new instance of our specific bean
        //(ExceptionResponse) and return exception resoponse back
        ExceptionResponse exceptionResponse
                = new ExceptionResponse
                (new Date(),
                        ex.getMessage(),
                        request.getDescription(false));

        return new ResponseEntity(exceptionResponse, HttpStatus.OK);
    }



}