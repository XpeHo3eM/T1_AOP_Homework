package ru.t1.java.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.t1.java.demo.dto.transaction.NewTransactionDto;
import ru.t1.java.demo.dto.transaction.TransactionDto;
import ru.t1.java.demo.service.TransactionService;

import java.util.Collection;

@RestController
@RequestMapping("/client/{clientId}")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @GetMapping("/transaction")
    public Collection<TransactionDto> getAllByClientId(@PathVariable Long clientId) {
        return transactionService.getAll(clientId);
    }

    @GetMapping("/account/{accountId}/transaction")
    public Collection<TransactionDto> getAllByAccountId(@PathVariable Long clientId,
                                                        @PathVariable Long accountId) {
        return transactionService.getAllByAccount(clientId, accountId);
    }


    @PostMapping("/account/{accountId}/transaction")
    public TransactionDto createTransaction(@PathVariable Long clientId,
                                            @PathVariable Long accountId,
                                            @RequestBody NewTransactionDto newTransactionDto) {
        return transactionService.create(newTransactionDto.toBuilder()
                .clientId(clientId)
                .accountId(accountId)
                .build());
    }

    @GetMapping("/transaction/{transactionId}")
    public TransactionDto getById(@PathVariable Long clientId,
                                  @PathVariable Long transactionId) {
        return transactionService.getById(clientId, transactionId);
    }
}
