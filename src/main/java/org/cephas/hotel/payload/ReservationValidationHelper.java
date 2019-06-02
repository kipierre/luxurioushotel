package org.cephas.hotel.payload;


import org.cephas.hotel.exception.DateSpecifiedAlreadyPassedException;
import org.cephas.hotel.exception.StartDateAfterEndDateException;

import java.time.ZonedDateTime;

public final class ReservationValidationHelper {

    private ReservationValidationHelper() {
    }

    public static void validateReservationTime(ZonedDateTime start, ZonedDateTime end) {
        if (start.isAfter(end)) {
            throw new StartDateAfterEndDateException();
        }

        if (ZonedDateTime.now().isAfter(start)) {
            throw new DateSpecifiedAlreadyPassedException();
        }
    }
}