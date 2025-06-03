package ru.t1.java.firstService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.t1.java.firstService.model.Transaction;
import ru.t1.java.general.enums.TransactionStatus;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Collection<Transaction> findAllByAccountId(Long accountId);

    Collection<Transaction> findAllByAccountIdIn(List<Long> accountIds);

    Collection<Transaction> findAllByTransactionIdIn(Collection<UUID> transactionIds);

    Collection<Transaction> findAllByTransactionIdInAndStatusNot(Collection<UUID> transactionIds, TransactionStatus status);
}
