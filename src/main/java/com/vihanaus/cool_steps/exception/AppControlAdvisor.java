package com.vihanaus.cool_steps.exception;

import com.vihanaus.cool_steps.controller.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @RestControllerAdvice
    public class AppControlAdvisor {

        @ExceptionHandler({NotFoundException.class})
        public ErrorResponse handleNotFoundException(Exception ex) {

            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setMessage(ex.getMessage());
            return errorResponse;

        }

}
