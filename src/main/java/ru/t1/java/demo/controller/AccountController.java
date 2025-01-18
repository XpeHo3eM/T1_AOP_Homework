package ru.t1.java.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.t1.java.demo.dto.account.AccountDto;
import ru.t1.java.demo.dto.account.NewAccountDto;
import ru.t1.java.demo.dto.account.UpdatedAccountDto;
import ru.t1.java.demo.service.AccountService;

import java.util.Collection;

@RestController
@RequestMapping("/client/{clientId}/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public AccountDto create(@PathVariable Long clientId, @RequestBody NewAccountDto newAccountDto) {
        return accountService.create(newAccountDto.toBuilder()
                .clientId(clientId)
                .build());
    }

    @GetMapping
    public Collection<AccountDto> getAllByClientId(@PathVariable Long clientId) {
        return accountService.getAll(clientId);
    }

    @GetMapping("/{id}")
    public AccountDto getById(@PathVariable Long id) {
        return accountService.getById(id);
    }

    @PatchMapping("/{accountId}")
    public AccountDto update(@PathVariable Long clientId, @PathVariable Long accountId, @RequestBody UpdatedAccountDto updatedAccountDto) {
        return accountService.update(updatedAccountDto.toBuilder()
                .clientId(clientId)
                .accountId(accountId)
                .build());
    }

    @DeleteMapping("/{accountId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long accountId) {
        accountService.delete(accountId);
    }
}
