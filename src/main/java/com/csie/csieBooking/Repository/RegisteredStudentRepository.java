package com.csie.csieBooking.Repository;

import com.csie.csieBooking.domain.RegisteredStudent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RegisteredStudentRepository extends JpaRepository<RegisteredStudent, String> {
    Optional<RegisteredStudent> findByStudentIdAndName(String studentId, String name);
    boolean existsByStudentIdAndName(String studentId, String name);
}
