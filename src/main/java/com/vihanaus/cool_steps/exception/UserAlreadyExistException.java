package com.vihanaus.cool_steps.exception;

public class UserAlreadyExistException extends NotFoundException{
    public UserAlreadyExistException(String message) {
        super(message);
    }
}
