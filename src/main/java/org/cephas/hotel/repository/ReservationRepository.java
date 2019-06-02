package org.cephas.hotel.repository;

import org.cephas.hotel.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Created by admin on 31-05-19.
 */
@Repository
public interface ReservationRepository extends JpaRepository<Reservation,Long> {

    @Query(value = "SELECT r FROM Reservation r " +
            "WHERE r.roomTypeId = ?1 AND " +
            "(?2 BETWEEN r.startDate AND r.endDate) OR " +
            "(?3 BETWEEN r.startDate AND r.endDate) OR " +
            "(?2 <= r.startDate AND ?3 >= r.endDate)",nativeQuery = true)
    List<Reservation> find(long roomTypeId, ZonedDateTime start, ZonedDateTime end);


    @Query(value = "SELECT r from Reservation r WHERE r.id = ?1 AND r.cancelled = ?2",nativeQuery = true)
    Optional<Reservation> findByIdAndAndCancelledForUpdate(Long id, Boolean cancelled);

    Optional<Reservation> findByIdAndAndCancelled(Long id, Boolean cancelled);
}
