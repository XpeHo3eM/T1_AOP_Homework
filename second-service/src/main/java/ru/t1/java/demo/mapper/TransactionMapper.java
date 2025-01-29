package ru.t1.java.demo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.t1.java.demo.dto.TransactionAcceptDto;
import ru.t1.java.demo.dto.TransactionResultDto;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    @Mapping(target = "status", ignore = true)
    TransactionResultDto toResult(TransactionAcceptDto transactionAcceptDto);
}
