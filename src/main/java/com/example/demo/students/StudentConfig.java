package com.example.demo.students;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

import static java.time.Month.*;

@Configuration
public class StudentConfig {

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository) {
        return args -> {
            Student dang = new Student(
                    1L,
                    "Dang",
                    "dangnh3@gmail.com",
                    LocalDate.of(1993, OCTOBER, 15)
            );

            Student nhi = new Student(
                    2L,
                    "Nhi",
                    "levy.17@gmail.com",
                    LocalDate.of(2000, FEBRUARY, 17)
            );

            Student david = new Student(
                    3L,
                    "David",
                    "david@gmail.com",
                    LocalDate.of(2004, DECEMBER, 25)
            );

            studentRepository.saveAll(
                    List.of(dang, nhi, david)
            );
        };
    }
}
