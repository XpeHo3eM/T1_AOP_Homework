package ru.t1.java.general.dto.transaction;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.t1.java.general.enums.TransactionStatus;

import java.io.Serializable;
import java.util.UUID;

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
