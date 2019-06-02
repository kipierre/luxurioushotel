package org.cephas.hotel.controller;


import org.cephas.hotel.converter.Constant;
import org.cephas.hotel.model.AvailableRoomType;
import org.cephas.hotel.service.RoomTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.List;
@RestController
@RequestMapping("room-types")

public class RoomTypeController {
@Autowired
    private RoomTypeService roomTypeService;

    @GetMapping("available")
    public List<AvailableRoomType> findAvailableRoomTypes(@RequestParam @DateTimeFormat(pattern = Constant.DATE_FORMAT) LocalDate start,
                                                          @RequestParam @DateTimeFormat(pattern = Constant.DATE_FORMAT) LocalDate end) {
        return roomTypeService.findAvailableRoomTypes(ZonedDateTime.of(start, LocalTime.MIN, Constant.ZONE_OFFSET),
                ZonedDateTime.of(end, LocalTime.MIN, Constant.ZONE_OFFSET));
    }
}
