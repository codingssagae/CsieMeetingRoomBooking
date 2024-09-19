package com.csie.csieBooking.service;

import com.csie.csieBooking.Repository.ReservationRepository;
import com.csie.csieBooking.domain.Member;
import com.csie.csieBooking.domain.Reservation;
import com.csie.csieBooking.exception.ReservationConflictException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public void createReservation(Member member, LocalDateTime startDateTime) throws ReservationConflictException {
        LocalDateTime endDateTime = startDateTime.plusHours(1);  // 예약을 1시간 단위로 설정

        // 기존 예약 중 시간이 겹치는 것이 있는지 확인
        List<Reservation> conflictingReservations = reservationRepository.findConflictingReservations(startDateTime, endDateTime);

        if (!conflictingReservations.isEmpty()) {
            throw new ReservationConflictException("이미 해당 시간에 예약이 존재합니다.");
        }

        // 예약 생성 및 저장
        Reservation reservation = new Reservation();
        reservation.setMember(member);
        reservation.setStartTime(startDateTime);
        reservation.setEndTime(endDateTime);

        reservationRepository.save(reservation);
    }

    public List<Reservation> getReservationsByMember(Member member) {
        return reservationRepository.findByMember(member);
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    // 특정 날짜의 예약 조회
    public List<Reservation> getReservationsByDate(LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.plusDays(1).atStartOfDay();
        return reservationRepository.findByStartTimeBetween(startOfDay, endOfDay);
    }

    @Transactional
    public void deleteReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("해당 예약이 존재하지 않습니다."));
        reservationRepository.delete(reservation);
    }

}
