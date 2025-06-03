package ru.t1.java.starter.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@RequiredArgsConstructor
public class HttpLogger {
    private final String type;

    @Around("@annotation(ru.t1.java.starter.annotation.LogHttp)")
    public Object logging(ProceedingJoinPoint proceedingJoinPoint) {
        String methodName = proceedingJoinPoint.getSignature().getName();
        String className = proceedingJoinPoint.getSignature().getDeclaringTypeName();

        logByType(String.format("Executing method: %s of class: %s", methodName, className));

        Object result = null;
        try {
            result = proceedingJoinPoint.proceed();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }

        logByType(String.format("Method executed: %s of class: %s, result: %s", methodName, className, result));

        return result;
    }

    private void logByType(String message) {
        switch (type) {
            case "INFO" -> log.info(message);
            case "DEBUG" -> log.debug(message);
            case "WARN" -> log.warn(message);
            case "ERROR" -> log.error(message);
        }
    }
}
