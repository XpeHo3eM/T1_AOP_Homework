package ru.t1.java.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transaction")
public class Transaction extends AbstractPersistable<Long> {
    @Column(name = "account_id")
    private Long accountId;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "create_at")
    private LocalDateTime createdAt;
}
