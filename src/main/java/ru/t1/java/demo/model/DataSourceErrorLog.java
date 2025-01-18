package ru.t1.java.demo.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class DataSourceErrorLog {
    String stack_trace;
    String message;
    String signature;
}
