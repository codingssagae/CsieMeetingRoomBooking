package com.csie.csieBooking.service;

import com.csie.csieBooking.Repository.MemberRepository;
import com.csie.csieBooking.Repository.RegisteredStudentRepository;
import com.csie.csieBooking.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final RegisteredStudentRepository registeredStudentRepository;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public String registerMember(String studentId, String name, String password){
        if (registeredStudentRepository.findByStudentIdAndName(studentId,name).isPresent()){
            if (memberRepository.existsByStudentId(studentId)) {
                return "이미 회원가입 하셨습니다.";
            }else {
                Member member = new Member();
                member.setStudentId(studentId);
                member.setName(name);
                member.setPassword(passwordEncoder.encode(password));

                memberRepository.save(member);
                return "회원가입 완료";
            }
        }else{
            return "등록된 컴퓨터정보 공학부 학생이 아니거나 학생회비 미납부자 입니다!";
        }
    }
}
