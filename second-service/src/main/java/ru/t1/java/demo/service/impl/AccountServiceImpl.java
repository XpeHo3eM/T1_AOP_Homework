package ru.t1.java.demo.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import ru.t1.java.demo.dto.transaction.TransactionAcceptDto;
import ru.t1.java.demo.mapper.AccountMapper;
import ru.t1.java.demo.model.Account;
import ru.t1.java.demo.repository.AccountRepository;
import ru.t1.java.demo.repository.ClientRepository;
import ru.t1.java.demo.service.AccountService;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Validated
public class AccountServiceImpl implements AccountService {
    private final ClientRepository clientRepository;
    private final AccountRepository accountRepository;
    private final AccountMapper mapper;

    private void assertClientExists(Long clientId) {
        if (!clientRepository.existsById(clientId)) {
            throw new EntityNotFoundException(String.format("Клиент с id = %d не найден", clientId));
        }
    }

    private Account getAccountOrThrowException(Long clientId, Long accountId) {
        return accountRepository.findByIdAndClientId(accountId, clientId)
                .orElseThrow(() -> new EntityNotFoundException(String.format(
                        "Пользователь с id = %d не имеет счета с id = %d", clientId, accountId)));
    }
}
