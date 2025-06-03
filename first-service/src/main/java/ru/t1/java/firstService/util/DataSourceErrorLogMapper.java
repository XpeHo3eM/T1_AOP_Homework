package ru.t1.java.firstService.util;

import org.mapstruct.Mapper;
import ru.t1.java.firstService.model.DataSourceErrorLog;
import ru.t1.java.general.dto.dataSourceErrorLog.DataSourceErrorLogDto;
import ru.t1.java.general.dto.dataSourceErrorLog.NewDataSourceErrorLogDto;

@Mapper(componentModel = "spring")
public interface DataSourceErrorLogMapper {
    DataSourceErrorLogDto toDto(DataSourceErrorLog dataSourceErrorLog);

    DataSourceErrorLog toLog(NewDataSourceErrorLogDto newDataSourceErrorLogDto);
}
