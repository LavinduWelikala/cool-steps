package com.vihanaus.cool_steps.exception;

public class DuplicateDeviceFoundException extends NotFoundException{

    public DuplicateDeviceFoundException(String message) {
        super(message);
    }
}
