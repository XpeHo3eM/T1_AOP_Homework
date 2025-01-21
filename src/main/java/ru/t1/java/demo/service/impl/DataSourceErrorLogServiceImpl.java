package ru.t1.java.demo.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.t1.java.demo.dto.dataSourceErrorLog.NewDataSourceErrorLogDto;
import ru.t1.java.demo.repository.DataSourceErrorLogRepository;
import ru.t1.java.demo.service.DataSourceErrorLogService;
import ru.t1.java.demo.util.DataSourceErrorLogMapper;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class DataSourceErrorLogServiceImpl implements DataSourceErrorLogService {
    private final DataSourceErrorLogRepository repository;
    private final DataSourceErrorLogMapper mapper;

    @Override
    public void create(NewDataSourceErrorLogDto newDataSourceErrorLogDto) {
        mapper.toDto(repository.save(mapper.toLog(newDataSourceErrorLogDto)));
    }
}
