package ru.t1.java.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.t1.java.demo.model.Account;

import java.util.Collection;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Collection<Account> findAllByClientId(Long clientId);

    Optional<Account> findByIdAndClientId(Long accountId, Long clientId);
}
