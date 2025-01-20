package ru.t1.java.demo.dto.account;


import lombok.Builder;
import lombok.Value;
import ru.t1.java.demo.model.Account;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.io.Serializable;

/**
 * DTO for {@link ru.t1.java.demo.model.Account}
 */
@Value
@Builder(toBuilder = true)
public class UpdatedAccountDto implements Serializable {
    @Positive
    Long clientId;

    @Positive
    Long accountId;

    @NotBlank
    Account.Type type;
    
    Double balance;
}
