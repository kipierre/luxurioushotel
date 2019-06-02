package org.cephas.hotel.service;

import org.cephas.hotel.exception.AvailableRoomsNotEnoughException;
import org.cephas.hotel.exception.NotFoundException;
import org.cephas.hotel.exception.ReservationStartDateHasPassedException;
import org.cephas.hotel.exception.ResourceNotFoundException;
import org.cephas.hotel.model.Reservation;
import org.cephas.hotel.model.RoomType;
import org.cephas.hotel.payload.ReservationRequest;
import org.cephas.hotel.payload.ReservationValidationHelper;
import org.cephas.hotel.repository.ReservationRepository;
import org.cephas.hotel.repository.RoomTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;

@Service

public class ReservationService {

    @Autowired
    private  ReservationRepository reservationRepository;
    @Autowired
    private  RoomTypeRepository roomTypeRepository;

    @Transactional
    public Reservation create(ReservationRequest request) {
        ReservationValidationHelper.validateReservationTime(request.getStartDate(), request.getEndDate());

        validateRoomAvailability(request);

        final Reservation reservation = new Reservation();
        reservation.setRoomTypeId(request.getRoomTypeId());
        reservation.setCustomerId(request.getCustomerId());
        reservation.setQuantity(request.getQuantity());
        reservation.setStartDate(request.getStartDate());
        reservation.setEndDate(request.getEndDate());
        reservation.setCancelled(Boolean.FALSE);

        return reservationRepository.save(reservation);
    }

    public Iterable<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    public Reservation find(Long id) {
        return reservationRepository.findByIdAndAndCancelled(id, Boolean.FALSE)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Reservation with id %d is not found or it has been cancelled", id)));
    }

    public Reservation findForUpdate(Long id) {
        return reservationRepository.findByIdAndAndCancelledForUpdate(id, Boolean.FALSE)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Reservation with id %d is not found or it has been cancelled", id)));
    }

    @Transactional
    public Reservation update(Long id, ReservationRequest request) {
        ReservationValidationHelper.validateReservationTime(request.getStartDate(), request.getEndDate());

        final Reservation reservation = findForUpdate(id);

        if (ZonedDateTime.now().isAfter(reservation.getStartDate())) {
            throw new ReservationStartDateHasPassedException();
        }

        final RoomType currentRoomType = getRoomType(reservation.getRoomTypeId());

        if (!request.getRoomTypeId().equals(currentRoomType)) {
            validateRoomAvailability(request);
            reservation.setRoomTypeId(request.getRoomTypeId());
        }

        reservation.setCustomerId(request.getCustomerId());
        reservation.setQuantity(request.getQuantity());
        reservation.setStartDate(request.getStartDate());
        reservation.setEndDate(request.getEndDate());

        return reservationRepository.save(reservation);
    }

    @Transactional
    public Reservation cancel(Long id) {
        final Reservation reservation = findForUpdate(id);

        reservation.setCancelled(Boolean.TRUE);

        return reservationRepository.save(reservation);
    }

    private void validateRoomAvailability(ReservationRequest request) {
        final RoomType roomType = getRoomType(request.getRoomTypeId());

        final List<Reservation> reservations = reservationRepository.find(roomType.getId(),
                request.getStartDate(), request.getEndDate());

        final Long reservedQuantity = reservations.stream()
                .mapToLong(reservation -> reservation.getQuantity())
                .sum();

        if (request.getQuantity() > (roomType.getQuantity() - reservedQuantity)) {
            throw new AvailableRoomsNotEnoughException();
        }
    }

    private RoomType getRoomType(Long id) {
        return roomTypeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(RoomType.class, id.toString()));
    }
}
