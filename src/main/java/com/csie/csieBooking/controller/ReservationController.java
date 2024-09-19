package com.csie.csieBooking.controller;

import com.csie.csieBooking.domain.Member;
import com.csie.csieBooking.domain.Reservation;
import com.csie.csieBooking.exception.ReservationConflictException;
import com.csie.csieBooking.security.CustomUserDetails;
import com.csie.csieBooking.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;


    @GetMapping("/reservation/new")
    public String reservationForm(){
        return "reservationForm";
    }

    @PostMapping("/reservation/new")
    public String createReservation(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam("reservationDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam("startTime") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime startTime,
            RedirectAttributes redirectAttributes) {


        // 날짜와 시간을 사용하여 LocalDateTime 생성
        LocalDateTime startDateTime = LocalDateTime.of(date, startTime);

        try {
            // 서비스로 데이터를 전달하여 예약 처리
            reservationService.createReservation(userDetails.getMember(), startDateTime);

            // 성공 메시지 설정
            redirectAttributes.addFlashAttribute("message", "예약이 성공적으로 완료되었습니다.");
            return "redirect:/main";
        } catch (ReservationConflictException e) {
            // 충돌 예외 발생 시 경고 메시지 설정
            redirectAttributes.addFlashAttribute("errorMessage", "해당 시간에는 이미 예약이 있습니다. 다른 시간을 선택해주세요.");
        }

        return "redirect:/reservation/new";
    }

    @GetMapping("/reservation/my")
    public String myReservations(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        Member member = userDetails.getMember();
        List<Reservation> myReservations = reservationService.getReservationsByMember(member);

        model.addAttribute("myReservations", myReservations);
        return "myReservations";
    }

    @PostMapping("/reservation/delete/{reservationId}")
    public String deleteReservation(@PathVariable Long reservationId, RedirectAttributes redirectAttributes) {
        try {
            reservationService.deleteReservation(reservationId);
            redirectAttributes.addFlashAttribute("message", "예약이 성공적으로 삭제되었습니다.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "예약 삭제 중 오류가 발생했습니다.");
        }
        return "redirect:/reservation/my";
    }


}
