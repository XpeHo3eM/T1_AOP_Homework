package ru.t1.java.general.dto.dataSourceErrorLog;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class DataSourceErrorLogDto {
    Long id;
    String stackTrace;
    String message;
    String signature;
}
