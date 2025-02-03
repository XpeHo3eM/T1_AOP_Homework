package ru.t1.java.firstService.util;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.t1.java.firstService.dto.transaction.NewTransactionDto;
import ru.t1.java.firstService.model.Transaction;
import ru.t1.java.general.dto.transaction.TransactionDto;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "status", constant = "REQUESTED")
    @Mapping(target = "transactionId", expression = "java(java.util.UUID.randomUUID())")
    @Mapping(target = "timestamp", expression = "java(java.time.LocalDateTime.now())")
    Transaction toTransaction(NewTransactionDto newTransactionDto);

    TransactionDto toDto(Transaction transaction);
}
