package ru.t1.java.demo.dto.transaction;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

/**
 * DTO for {@link ru.t1.java.demo.model.Transaction}
 */
@Value
@Builder(toBuilder = true)
public class TransactionDto {
    Long id;
    Long accountId;
    Double amount;
    LocalDateTime createdAt;
}
