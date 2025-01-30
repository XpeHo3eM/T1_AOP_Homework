package ru.t1.java.demo.dto.dataSourceErrorLog;

import lombok.Builder;
import lombok.Value;

/**
 * DTO for {@link ru.t1.java.demo.model.DataSourceErrorLog}
 */
@Value
@Builder
public class DataSourceErrorLogDto {
    Long id;
    String stackTrace;
    String message;
    String signature;
}
