package ru.t1.java.demo.dto.transaction;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for {@link ru.t1.java.demo.model.Transaction}
 */
@Value
@Builder(toBuilder = true)
public class TransactionAcceptDto implements Serializable {
    @JsonProperty("client_id")
    UUID clientId;

    @JsonProperty("account_id")
    UUID accountId;

    @JsonProperty("transaction_id")
    UUID transactionId;

    @JsonProperty("transaction_amount")
    Double transactionAmount;

    @JsonProperty("account_balance")
    Double accountBalance;

    @JsonProperty("timestamp")
    LocalDateTime timestamp;
}
