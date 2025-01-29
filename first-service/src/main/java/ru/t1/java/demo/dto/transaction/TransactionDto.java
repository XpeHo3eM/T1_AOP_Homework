package ru.t1.java.demo.dto.transaction;

import lombok.Builder;
import lombok.Value;
import ru.t1.java.demo.model.enums.TransactionStatus;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for {@link ru.t1.java.demo.model.Transaction}
 */
@Value
@Builder(toBuilder = true)
public class TransactionDto implements Serializable {
    Long id;
    Long accountId;
    Double amount;
    LocalDateTime createdAt;
    TransactionStatus status;
    UUID transactionId;
    LocalDateTime timestamp;
}
