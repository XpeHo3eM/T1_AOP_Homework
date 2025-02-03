package ru.t1.java.firstService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class FirstService {

    public static void main(String[] args) {
        SpringApplication.run(FirstService.class, args);
    }

}
