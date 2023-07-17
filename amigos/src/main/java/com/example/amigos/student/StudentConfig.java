package com.example.amigos.student;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StudentConfig {
    
    @Bean
    CommandLineRunner commandLineRunner(StudentRepository repository) {
        return args -> {
            Student robert = new Student(
                "Robert",
                "robert.schmidt@nevexo.ro",
                LocalDate.of(2002, Month.SEPTEMBER, 19)
            );

            Student robi = new Student(
                "Robik",
                "roberto.stefan@nevexo.ro",
                LocalDate.of(2002, Month.AUGUST, 13)
            );

            repository.saveAll(
                List.of(robert, robi)
            );
        };
    }
}
