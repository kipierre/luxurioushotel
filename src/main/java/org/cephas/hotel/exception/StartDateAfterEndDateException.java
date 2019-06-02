package org.cephas.hotel.exception;



public class StartDateAfterEndDateException extends ShbsException {
    public StartDateAfterEndDateException() {
        super("Start date must be before end date");
    }

    @Override
    public String getErrorCode() {
        return "start_date_after_end_date";
    }
}
