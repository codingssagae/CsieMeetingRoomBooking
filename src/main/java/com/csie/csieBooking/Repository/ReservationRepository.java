package com.csie.csieBooking.Repository;

import com.csie.csieBooking.domain.Reservation;
import com.csie.csieBooking.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByMember(Member member);
    List<Reservation> findByStartTimeBetween(LocalDateTime startOfDay, LocalDateTime endOfDay);

    // 시작 시간 또는 종료 시간이 기존 예약과 겹치는 예약 조회
    @Query("SELECT r FROM Reservation r WHERE " +
            "(r.startTime < :endTime AND r.endTime > :startTime)")
    List<Reservation> findConflictingReservations(@Param("startTime") LocalDateTime startTime,
                                                  @Param("endTime") LocalDateTime endTime);

}
