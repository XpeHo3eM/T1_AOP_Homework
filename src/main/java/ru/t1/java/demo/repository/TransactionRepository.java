package ru.t1.java.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.t1.java.demo.model.Transaction;

import java.util.Collection;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Collection<Transaction> findAllByAccountId(Long accountId);
}
