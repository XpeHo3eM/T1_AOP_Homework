package ru.t1.java.demo.service;

import ru.t1.java.demo.dto.dataSourceErrorLog.DataSourceErrorLogDto;

public interface DataSourceErrorLogService {
    DataSourceErrorLogDto create(DataSourceErrorLogDto dataSourceErrorLogDto);

    DataSourceErrorLogDto getById(Long id);
}
