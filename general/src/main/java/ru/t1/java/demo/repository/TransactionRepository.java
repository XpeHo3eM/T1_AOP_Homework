package ru.t1.java.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.t1.java.demo.model.Transaction;

import java.util.Collection;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("SELECT t" +
            " FROM Transaction t" +
            " WHERE t.accountId = :accountId" +
            " ORDER BY t.timestamp DESC" +
            " LIMIT :limit")
    Collection<Transaction> findAllByAccountIdWithLimit(Long accountId, Long limit);
    Collection<Transaction> findAllByAccountId(Long accountId);
    Collection<Transaction> findAllByAccountIdIn(List<Long> accountIds);
}
