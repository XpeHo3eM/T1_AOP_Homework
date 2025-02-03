package ru.t1.java.firstService.controller;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.t1.java.general.annotation.LogDataSourceException;
import ru.t1.java.general.annotation.Metric;

@RestController
@RequestMapping("/test")
public class KafkaTestController {
    @GetMapping("/long")
    @Metric(time = 25)
    public void longTimeWorkingMethod() {
        try {
            Thread.sleep(100L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/short")
    @Metric
    public void shortTimeWorkingMethod() {
    }

    @PostMapping("/crud")
    @LogDataSourceException
    public void crudException() {
        throw new EntityNotFoundException("Ошибка CRUD");
    }
}
