package ru.t1.java.secondService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.t1.java.secondService.model.DataSourceErrorLog;

public interface DataSourceErrorLogRepository extends JpaRepository<DataSourceErrorLog, Long> {
}
