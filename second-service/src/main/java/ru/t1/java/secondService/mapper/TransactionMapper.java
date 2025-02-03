package ru.t1.java.secondService.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.t1.java.general.dto.transaction.TransactionAcceptDto;
import ru.t1.java.general.dto.transaction.TransactionResultDto;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    @Mapping(target = "status", ignore = true)
    TransactionResultDto toResult(TransactionAcceptDto transactionAcceptDto);
}
