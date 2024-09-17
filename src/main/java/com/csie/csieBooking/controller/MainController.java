package com.csie.csieBooking.controller;

import com.csie.csieBooking.domain.Reservation;
import com.csie.csieBooking.security.CustomUserDetails;
import com.csie.csieBooking.service.ReservationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
@Controller
@RequiredArgsConstructor
public class MainController {

    private final ReservationService reservationService;

    @GetMapping("/main")
    public String mainPage(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam(value = "date", required = false) LocalDate date,
            Model model) {

        String name = userDetails.getName();

        // 선택한 날짜가 없으면 오늘 날짜로 설정
        if (date == null) {
            date = LocalDate.now();
        }

        // 선택한 날짜의 예약 정보 조회
        List<Reservation> reservations = reservationService.getReservationsByDate(date);

        // 예약자 이름과 예약 시간 목록 생성
        List<Map<String, String>> reservationDetails = reservations.stream()
                .map(reservation -> Map.of(
                        "name", reservation.getMember().getName(),
                        "time", String.format("%s - %s",
                                reservation.getStartTime().format(DateTimeFormatter.ofPattern("HH:mm")),
                                reservation.getEndTime().format(DateTimeFormatter.ofPattern("HH:mm"))
                        )
                ))
                .collect(Collectors.toList());

        // 이전 날짜와 다음 날짜 계산
        LocalDate previousDate = date.minusDays(1);
        LocalDate nextDate = date.plusDays(1);

        model.addAttribute("name", name);
        model.addAttribute("selectedDate", date);
        model.addAttribute("reservationDetails", reservationDetails);
        model.addAttribute("previousDate", previousDate);
        model.addAttribute("nextDate", nextDate);

        return "main";
    }
}

