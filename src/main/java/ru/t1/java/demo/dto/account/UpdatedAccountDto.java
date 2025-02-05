package ru.t1.java.demo.dto.account;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.t1.java.demo.model.AccountType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * DTO for {@link ru.t1.java.demo.model.Account}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdatedAccountDto {
    private Long clientId;
    private Long accountId;

    @NotBlank
    @JsonProperty("type")
    private AccountType type;

    @NotNull
    @JsonProperty("balance")
    private Double balance;
}
