package com.csie.csieBooking.CSV;

import com.csie.csieBooking.Repository.RegisteredStudentRepository;
import com.csie.csieBooking.domain.RegisteredStudent;
import com.opencsv.CSVReader;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.Reader;

@Component
@RequiredArgsConstructor
public class CSVDataLoader implements CommandLineRunner {

    private final RegisteredStudentRepository registeredStudentRepository;

    @Override
    public void run(String... args) throws Exception {
        // CSV 파일을 읽어서 RegisteredStudent 테이블에 저장
        try (Reader reader = new FileReader("src/main/resources/students.csv");
             CSVReader csvReader = new CSVReader(reader)) {

            String[] line;
            while ((line = csvReader.readNext()) != null) {
                String studentId = line[0].replace("\uFEFF", "").trim(); // String 그대로 처리
                String name = line[1].trim();

                RegisteredStudent student = new RegisteredStudent();
                student.setStudentId(studentId);
                student.setName(name);
                registeredStudentRepository.save(student);
            }
        }
    }
}
