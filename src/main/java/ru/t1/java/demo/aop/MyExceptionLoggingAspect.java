package ru.t1.java.demo.aop;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import ru.t1.java.demo.dto.dataSourceErrorLog.DataSourceErrorLogDto;
import ru.t1.java.demo.service.DataSourceErrorLogService;

import java.util.Arrays;

@Aspect
@Component
@RequiredArgsConstructor
public class MyExceptionLoggingAspect {
    private final DataSourceErrorLogService dataSourceErrorLogService;

    @AfterThrowing(
            pointcut = "@annotation(ru.t1.java.demo.aop.LogMyException)",
            throwing = "ex")
    public void logging(JoinPoint joinPoint, Throwable ex) {
        dataSourceErrorLogService.create(DataSourceErrorLogDto.builder()
                .stackTrace(Arrays.toString(ex.getStackTrace()))
                .message(ex.getMessage())
                .signature(joinPoint.getSignature().getName())
                .build());
    }
}
