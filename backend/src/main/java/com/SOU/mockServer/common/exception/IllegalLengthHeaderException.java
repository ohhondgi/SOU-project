package com.SOU.mockServer.common.exception;

import java.io.IOException;

public class IllegalLengthHeaderException extends IOException {
    public IllegalLengthHeaderException(String message){
        super(message);
    }
}
