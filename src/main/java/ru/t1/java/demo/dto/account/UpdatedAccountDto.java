package ru.t1.java.demo.dto.account;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ru.t1.java.demo.enums.AccountType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * DTO for {@link ru.t1.java.demo.model.Account}
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
