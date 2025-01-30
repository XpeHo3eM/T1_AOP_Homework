package ru.t1.java.demo.dto.transaction;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import ru.t1.java.demo.enums.TransactionStatus;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link ru.t1.java.demo.model.Transaction}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class TransactionResultDto implements Serializable {
    @JsonProperty("account_id")
    UUID accountId;

    @JsonProperty("transaction_id")
    UUID transactionId;

    @JsonProperty("status")
    @Enumerated(EnumType.STRING)
    TransactionStatus status;
}
