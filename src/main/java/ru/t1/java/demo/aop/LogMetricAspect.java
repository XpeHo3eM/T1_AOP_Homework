package ru.t1.java.demo.aop;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import ru.t1.java.demo.annotation.Metric;
import ru.t1.java.demo.dto.dataSourceErrorLog.NewDataSourceErrorLogDto;
import ru.t1.java.demo.dto.metric.NewMetricDto;
import ru.t1.java.demo.exception.KafkaException;
import ru.t1.java.demo.kafka.KafkaMetricProducer;
import ru.t1.java.demo.service.DataSourceErrorLogService;

import java.lang.reflect.Method;
import java.util.Arrays;

@Aspect
@Component
@RequiredArgsConstructor
public class LogMetricAspect {
    private final DataSourceErrorLogService dataSourceErrorLogService;
    private final KafkaMetricProducer kafkaMetricProducer;

    @Around("@annotation(ru.t1.java.demo.annotation.Metric)")
    public Object logging(ProceedingJoinPoint proceedingJoinPoint) {
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = signature.getMethod();
        Metric metric = method.getAnnotation(Metric.class);
        final long metricTimeout = metric.time();

        final long start = System.currentTimeMillis();
        Object result = null;

        try {
            result = proceedingJoinPoint.proceed();
        } catch (Throwable throwable) {
            dataSourceErrorLogService.create(generateLog(throwable, proceedingJoinPoint));
        }
        final long execTime = System.currentTimeMillis() - start;

        if (execTime > metricTimeout) {
            try {
                kafkaMetricProducer.send(NewMetricDto.builder()
                        .execTime(execTime)
                        .methodName(proceedingJoinPoint.getSignature().getDeclaringTypeName() + "." + proceedingJoinPoint.getSignature().getName())
                        .args(proceedingJoinPoint.getArgs())
                        .build());
            } catch (KafkaException e) {
                dataSourceErrorLogService.create(generateLog(e, proceedingJoinPoint));
            }
        }

        return result;
    }

    private NewDataSourceErrorLogDto generateLog(Throwable e, ProceedingJoinPoint proceedingJoinPoint) {
        return NewDataSourceErrorLogDto.builder()
                .message(e.getMessage())
                .signature(proceedingJoinPoint.getSignature().toShortString())
                .stackTrace(Arrays.toString(e.getStackTrace()).substring(0, 255))
                .build();
    }
}
