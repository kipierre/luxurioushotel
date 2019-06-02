package org.cephas.hotel.exception;


public class RoomTypeNotAvailableException extends ShbsException {
    public RoomTypeNotAvailableException() {
        super("All the rooms with type you specified have been occupied");
    }

    @Override
    public String getErrorCode() {
        return "room_type_not_available";
    }
}
