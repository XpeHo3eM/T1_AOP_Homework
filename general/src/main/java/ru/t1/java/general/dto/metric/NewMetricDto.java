package ru.t1.java.general.dto.metric;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class NewMetricDto {
    Long execTime;
    String methodName;
    Object[] args;
}
