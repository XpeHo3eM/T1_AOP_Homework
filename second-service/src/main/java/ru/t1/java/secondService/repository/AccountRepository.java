package ru.t1.java.secondService.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.t1.java.secondService.model.Account;

import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByAccountId(UUID accountUUID);
}
