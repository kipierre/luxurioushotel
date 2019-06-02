package org.cephas.hotel.service;


import org.cephas.hotel.exception.NotFoundException;
import org.cephas.hotel.model.AvailableRoomType;
import org.cephas.hotel.model.ReservedRoomType;
import org.cephas.hotel.model.RoomType;
import org.cephas.hotel.payload.ReservationValidationHelper;
import org.cephas.hotel.repository.RoomTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RoomTypeService {

    @Autowired
    private  RoomTypeRepository roomTypeRepository;

    public Iterable<RoomType> findAll() {
        return roomTypeRepository.findAll();
    }

    public List<AvailableRoomType> findAvailableRoomTypes(ZonedDateTime start, ZonedDateTime end) {
        ReservationValidationHelper.validateReservationTime(start, end);

        final List<ReservedRoomType> reservedRoomTypes = roomTypeRepository.findReservedRoomTypes(start, end);
        final Map<Long, Integer> reservedRoomTypeMap = new HashMap<>();

        reservedRoomTypes.forEach(reservedRoomType ->
                reservedRoomTypeMap.put(reservedRoomType.getId(), reservedRoomType.getReservedQuantity().intValue()));

        final List<AvailableRoomType> availableRoomTypes = roomTypeRepository
                .findByQuantityGreaterThan(0)
                .stream()
                .map(roomType -> {
                    if (reservedRoomTypeMap.containsKey(roomType.getId())) {
                        final Integer reservedQuantity = reservedRoomTypeMap.get(roomType.getId());
                        final Long availableQuantity = roomType.getQuantity() - reservedQuantity;

                        return new AvailableRoomType(roomType, availableQuantity);
                    }

                    return new AvailableRoomType(roomType);
                })
                .filter(availableRoomType -> availableRoomType.getAvailableQuantity() > 0)
                .collect(Collectors.toList());

        return availableRoomTypes;
    }

    public RoomType findById(Long id) {
        return roomTypeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(RoomType.class, id.toString()));
    }



    @Transactional
    public void delete(Long id) {
        findById(id);
        roomTypeRepository.delete(id);
    }
}
