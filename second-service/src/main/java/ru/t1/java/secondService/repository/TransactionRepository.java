package ru.t1.java.secondService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.t1.java.secondService.model.Transaction;

import java.util.Collection;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("SELECT t" +
            " FROM Transaction t" +
            " WHERE t.accountId = :accountId" +
            " ORDER BY t.timestamp DESC" +
            " LIMIT :limit")
    Collection<Transaction> findAllByAccountIdWithLimit(Long accountId, Long limit);
}
