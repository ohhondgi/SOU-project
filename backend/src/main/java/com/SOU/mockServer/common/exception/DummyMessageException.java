package com.SOU.mockServer.common.exception;

import java.io.IOException;

public class DummyMessageException extends IOException {
    public DummyMessageException(String message) {
        super(message);
    }
}
