package org.cephas.hotel.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Created by admin on 31-05-19.
 */
public class AvailableRoomType {
    @NotNull
    private Long id;

    @NotBlank
    private String type;

    private String description;
    private String image;

    @NotNull
    private Long availableQuantity;

    @NotNull
    private BigDecimal price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Long getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(Long availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public AvailableRoomType(RoomType roomType, Long availableQuantity) {
        this.id = roomType.getId();
        this.type = roomType.getType();
        this.description = roomType.getDescription();
        this.image = roomType.getImage();
        this.availableQuantity = availableQuantity;
        this.price = roomType.getPrice();
    }

    public AvailableRoomType(RoomType roomType) {
        this.id = roomType.getId();
        this.type = roomType.getType();
        this.description = roomType.getDescription();
        this.image = roomType.getImage();
        this.availableQuantity = roomType.getQuantity();
        this.price = roomType.getPrice();
    }
}
