package ru.t1.java.firstService.dto.account;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.t1.java.firstService.model.Account;
import ru.t1.java.general.enums.AccountType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * DTO for {@link Account}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdatedAccountDto implements Serializable {
    Long clientId;
    Long accountId;

    @NotBlank
    @JsonProperty("type")
    AccountType type;

    @NotNull
    @JsonProperty("balance")
    Double balance;
}
