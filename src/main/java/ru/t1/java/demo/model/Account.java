package ru.t1.java.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "account")
public class Account extends AbstractPersistable<Long> {
    @Column(name = "client_id")
    private Long clientId;

    @Column(name = "type")
    private Type type;

    @Column(name = "balance")
    private Double balance;

    public enum Type {
        DEBIT,
        CREDIT
    }
}
