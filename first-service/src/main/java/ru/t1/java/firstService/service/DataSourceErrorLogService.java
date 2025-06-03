package ru.t1.java.firstService.service;

import ru.t1.java.general.dto.dataSourceErrorLog.NewDataSourceErrorLogDto;

public interface DataSourceErrorLogService {
    void create(NewDataSourceErrorLogDto newDataSourceErrorLogDto);
}
