package ru.t1.java.general.dto.dataSourceErrorLog;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Value
@Builder
public class NewDataSourceErrorLogDto {
    @NotBlank
    @Size(max = 255)
    String stackTrace;

    @NotBlank
    @Size(max = 255)
    String message;

    @NotBlank
    @Size(max = 255)
    String signature;
}
