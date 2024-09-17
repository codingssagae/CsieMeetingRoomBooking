package com.csie.csieBooking.Repository;

import com.csie.csieBooking.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByStudentId(String studentId);

    Optional<Member> findByStudentId(String studentId);

}
