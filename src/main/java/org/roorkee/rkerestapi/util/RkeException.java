package org.roorkee.rkerestapi.util;

public class RkeException extends RuntimeException{

    public RkeException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getMessage() {
        return super.getCause().getMessage();
    }
}
