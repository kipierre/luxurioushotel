package org.cephas.hotel.exception;


import org.cephas.hotel.converter.Constant;

public class NotFoundException extends ShbsException {

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(Class clazz, String entityId) {
        this(clazz.getSimpleName(), entityId);
    }

    public NotFoundException(String entityName, String entityId) {
        super(String.format(Constant.LOCALE, "%s with Id %s is not found", entityName, entityId));
    }

    @Override
    public String getErrorCode() {
        return "not_found";
    }
}
