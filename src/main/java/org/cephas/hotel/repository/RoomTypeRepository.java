package org.cephas.hotel.repository;

import org.cephas.hotel.model.ReservedRoomType;
import org.cephas.hotel.model.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Created by admin on 31-05-19.
 */
@Repository
public interface RoomTypeRepository extends JpaRepository<RoomType,Long> {

    @Query(value = "SELECT new org.cephas.hotel.model.roomtype.ReservedRoomType(r.roomTypeId, SUM(r.quantity)) " +
            "FROM Reservation r WHERE " +
            "r.cancelled = FALSE AND " +
            "((?1 BETWEEN r.startDate AND r.endDate) OR " +
            "(?2 BETWEEN r.startDate AND r.endDate) OR " +
            "(?1 <= r.startDate AND ?2 >= r.endDate)) " +
            "GROUP BY r.roomTypeId")
    List<ReservedRoomType> findReservedRoomTypes(ZonedDateTime start, ZonedDateTime end);

    List<RoomType> findByQuantityGreaterThan(long value);

    void delete(Long id);
}
