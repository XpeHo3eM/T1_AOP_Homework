package ru.t1.java.demo.dto.account;

import lombok.Builder;
import lombok.Data;
import ru.t1.java.demo.model.Account;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;

/**
 * DTO for {@link ru.t1.java.demo.model.Account}
 */
@Data
@Builder(toBuilder = true)
public class NewAccountDto implements Serializable {
    private Long clientId;

    @NotNull
    private Account.Type type;
}
