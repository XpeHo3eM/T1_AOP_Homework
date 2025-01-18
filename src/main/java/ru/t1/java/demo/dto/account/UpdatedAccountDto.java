package ru.t1.java.demo.dto.account;


import lombok.Builder;
import lombok.Value;
import ru.t1.java.demo.model.Account;

import java.io.Serializable;

/**
 * DTO for {@link ru.t1.java.demo.model.Account}
 */
@Value
@Builder(toBuilder = true)
public class UpdatedAccountDto implements Serializable {
    Long clientId;
    Long accountId;
    Account.Type type;
    Double balance;
}
