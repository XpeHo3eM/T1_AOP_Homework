package ru.t1.java.demo.util;

import org.mapstruct.Mapper;
import ru.t1.java.demo.dto.dataSourceErrorLog.DataSourceErrorLogDto;
import ru.t1.java.demo.dto.dataSourceErrorLog.NewDataSourceErrorLogDto;
import ru.t1.java.demo.model.DataSourceErrorLog;

@Mapper(componentModel = "spring")
public interface DataSourceErrorLogMapper {
    DataSourceErrorLogDto toDto(DataSourceErrorLog dataSourceErrorLog);
    DataSourceErrorLog toLog(NewDataSourceErrorLogDto newDataSourceErrorLogDto);
}
