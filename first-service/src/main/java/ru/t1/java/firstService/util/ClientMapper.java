package ru.t1.java.firstService.util;

import org.springframework.stereotype.Component;
import ru.t1.java.firstService.model.Client;
import ru.t1.java.general.dto.client.ClientDto;

@Component
public class ClientMapper {

    public static Client toEntity(ClientDto dto) {
        if (dto.getMiddleName() == null) {
//            throw new NullPointerException();
        }
        return Client.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .middleName(dto.getMiddleName())
                .build();
    }

    public static ClientDto toDto(Client entity) {
        return ClientDto.builder()
                .id(entity.getId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .middleName(entity.getMiddleName())
                .build();
    }

}
