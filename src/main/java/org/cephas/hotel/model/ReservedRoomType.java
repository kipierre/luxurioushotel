package org.cephas.hotel.model;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by admin on 31-05-19.
 */
public class ReservedRoomType  {
    @NotNull
    private Long id;

    @NotNull
    private Long reservedQuantity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Long getReservedQuantity() {
        return reservedQuantity;
    }

    public void setReservedQuantity(Long reservedQuantity) {
        this.reservedQuantity = reservedQuantity;
    }
}
