package ru.t1.java.demo.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.t1.java.demo.dto.dataSourceErrorLog.DataSourceErrorLogDto;
import ru.t1.java.demo.repository.DataSourceErrorLogRepository;
import ru.t1.java.demo.service.DataSourceErrorLogService;
import ru.t1.java.demo.util.DataSourceErrorLogMapper;

@Service
@RequiredArgsConstructor
@Transactional
public class DataSourceErrorLogServiceImpl implements DataSourceErrorLogService {
    private final DataSourceErrorLogRepository repository;
    private final DataSourceErrorLogMapper mapper;

    @Override
    public DataSourceErrorLogDto create(DataSourceErrorLogDto dataSourceErrorLogDto) {
        return mapper.toDto(repository.save(mapper.toLog(dataSourceErrorLogDto)));
    }

    @Override
    @Transactional(readOnly = true)
    public DataSourceErrorLogDto getById(Long id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Лог с id = %d не найден", id)));
    }
}
