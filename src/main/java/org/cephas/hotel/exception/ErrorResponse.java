package org.cephas.hotel.exception;




public class ErrorResponse {

    private final String error;
    private final String errorDescription;

    public ErrorResponse(String errorCode, String message) {
        error = null;
        errorDescription= null;
    }

    public String getError() {
        return error;
    }

    public String getErrorDescription() {
        return errorDescription;
    }
}
