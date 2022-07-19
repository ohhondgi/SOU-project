package com.SOU.mockServer.common.exception;

import java.io.IOException;

public class ExceededMaxMessageLengthException extends IOException {
    public ExceededMaxMessageLengthException(String message) {
        super(message);
    }

}
