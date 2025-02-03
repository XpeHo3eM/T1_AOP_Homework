package ru.t1.java.general.dto.transaction;

import lombok.Builder;
import lombok.Value;
import ru.t1.java.general.enums.TransactionStatus;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

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
