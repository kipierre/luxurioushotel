package org.cephas.hotel.controller;


import org.cephas.hotel.model.Reservation;
import org.cephas.hotel.payload.ReservationRequest;
import org.cephas.hotel.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("reservations")

public class ReservationController {
@Autowired
    private ReservationService reservationService;

    @PostMapping
    public ResponseEntity<Reservation> create(@Validated @RequestBody ReservationRequest request) {
        return new ResponseEntity<>(reservationService.create(request), HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public Reservation find(@PathVariable Long id) {
        return reservationService.find(id);
    }

    @PatchMapping("{id}")
    public Reservation update(@PathVariable Long id , @Validated @RequestBody ReservationRequest request) {
        return reservationService.update(id, request);
    }

    @PatchMapping("{id}/cancel")
    public Reservation cancel(@PathVariable Long id) {
        return reservationService.cancel(id);
    }
}
