package ru.t1.java.firstService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.t1.java.firstService.model.Account;
import ru.t1.java.general.enums.AccountStatus;

import java.util.Collection;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Collection<Account> findAllByClientId(Long clientId);

    Optional<Account> findByIdAndClientId(Long accountId, Long clientId);

    Boolean existsByIdAndStatus(Long accountId, AccountStatus status);

    Collection<Account> findAllByIdIn(Collection<Long> accountIds);
}
