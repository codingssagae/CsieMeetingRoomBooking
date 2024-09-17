package com.csie.csieBooking.controller;

import com.csie.csieBooking.Repository.MemberRepository;
import com.csie.csieBooking.Repository.RegisteredStudentRepository;
import com.csie.csieBooking.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/register")
    public String showRegisterForm(){
        return "register";
    }

    @PostMapping("/register")
    public String registerMember(@RequestParam("studentId") String studentId,
                                 @RequestParam("name") String name,
                                 @RequestParam("password") String password,
                                 @RequestParam("passwordConfirm") String passwordConfirm,
                                 @RequestParam(value = "agreement", required = false) String agreement,
                                 RedirectAttributes redirectAttributes, Model model){
        // 체크박스가 체크되지 않았을 경우
        if (agreement == null) {
            model.addAttribute("message", "타인의 학번을 도용하지 않았음을 확인해야 합니다.");
            return "register";
        }
        // 비밀번호와 비밀번호 확인이 일치하지 않는 경우
        if (!password.equals(passwordConfirm)) {
            model.addAttribute("message", "비밀번호가 일치하지 않습니다.");
            return "register";
        }
        String resultMessage = memberService.registerMember(studentId, name, password);
        if ("회원가입 완료".equals(resultMessage)) {
            // 성공 시 로그인 페이지로 리다이렉트
            redirectAttributes.addFlashAttribute("successMessage", "회원가입이 완료되었습니다. 로그인 해주세요.");
            return "redirect:/login";  // 로그인 페이지로 리다이렉트
        } else {
            // 실패 시 회원가입 폼으로 다시 이동
            model.addAttribute("message", resultMessage);
            return "register";
        }

    }

}
