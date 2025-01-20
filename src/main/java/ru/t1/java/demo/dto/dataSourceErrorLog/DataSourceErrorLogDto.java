package ru.t1.java.demo.dto.dataSourceErrorLog;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class DataSourceErrorLogDto {
    String stackTrace;
    String message;
    String signature;
}
