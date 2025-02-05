package ru.t1.java.demo.service;

import ru.t1.java.demo.dto.dataSourceErrorLog.NewDataSourceErrorLogDto;

public interface DataSourceErrorLogService {
    void create(NewDataSourceErrorLogDto newDataSourceErrorLogDto);
}
