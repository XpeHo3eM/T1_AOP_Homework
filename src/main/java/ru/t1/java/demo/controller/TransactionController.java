package ru.t1.java.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.t1.java.demo.dto.transaction.NewTransactionDto;
import ru.t1.java.demo.dto.transaction.TransactionDto;
import ru.t1.java.demo.service.TransactionService;

import java.util.Collection;

@RestController
@RequestMapping("/client/{clientId}/account/{accountId}/transaction/")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @GetMapping
    public Collection<TransactionDto> getAllByClientId(@PathVariable Long accountId) {
        return transactionService.getAll(accountId);
    }

    @PostMapping
    public TransactionDto createTransaction(@RequestBody NewTransactionDto newTransactionDto) {
        return transactionService.create(newTransactionDto);
    }

    @GetMapping("/{transactionId}")
    public TransactionDto getById(@PathVariable Long transactionId) {
        return transactionService.getById(transactionId);
    }
}
