package ru.t1.java.demo.dto.transaction;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;

/**
 * DTO for {@link ru.t1.java.demo.model.Transaction}
 */
@Data
public class NewTransactionDto implements Serializable {
    @NotNull
    @Positive
    private Long accountId;

    @NotNull
    private Double amount;
}
