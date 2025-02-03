package ru.t1.java.firstService.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.t1.java.general.dto.account.AccountDto;
import ru.t1.java.firstService.dto.account.NewAccountDto;
import ru.t1.java.firstService.dto.account.UpdatedAccountDto;
import ru.t1.java.firstService.service.AccountService;

import java.util.Collection;

@RestController
@RequestMapping("/client/{clientId}/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public AccountDto create(@PathVariable Long clientId,
                             @RequestBody NewAccountDto newAccountDto) {
        return accountService.create(NewAccountDto.builder()
                .clientId(clientId)
                .type(newAccountDto.getType())
                .build());
    }

    @GetMapping
    public Collection<AccountDto> getAllByClientId(@PathVariable Long clientId) {
        return accountService.getAll(clientId);
    }

    @GetMapping("/{accountId}")
    public AccountDto getById(@PathVariable Long clientId,
                              @PathVariable Long accountId) {
        return accountService.getById(clientId, accountId);
    }

    @PatchMapping("/{accountId}")
    public AccountDto update(@PathVariable Long clientId,
                             @PathVariable Long accountId,
                             @RequestBody UpdatedAccountDto updatedAccountDto) {
        return accountService.update(updatedAccountDto.toBuilder()
                .clientId(clientId)
                .accountId(accountId)
                .build());
    }

    @DeleteMapping("/{accountId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long clientId,
                       @PathVariable Long accountId) {
        accountService.delete(clientId, accountId);
    }
}
