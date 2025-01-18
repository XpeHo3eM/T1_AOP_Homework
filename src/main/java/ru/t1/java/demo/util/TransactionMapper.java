package ru.t1.java.demo.util;

import org.mapstruct.Mapper;
import ru.t1.java.demo.dto.transaction.NewTransactionDto;
import ru.t1.java.demo.dto.transaction.TransactionDto;
import ru.t1.java.demo.model.Transaction;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    Transaction toTransaction(NewTransactionDto newTransactionDto);
    TransactionDto toDto(Transaction transaction);
}
