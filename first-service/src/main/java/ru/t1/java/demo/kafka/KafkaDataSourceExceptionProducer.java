package ru.t1.java.demo.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import ru.t1.java.demo.dto.dataSourceErrorLog.NewDataSourceErrorLogDto;
import ru.t1.java.demo.kafka.enums.KafkaHeader;
import ru.t1.java.demo.exception.KafkaException;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public class KafkaDataSourceExceptionProducer {
    private final KafkaTemplate template;

    public void send(NewDataSourceErrorLogDto newDataSourceErrorLogDto) {
        try {
            template.send(createProcedureRecord(newDataSourceErrorLogDto, KafkaHeader.DATA_SOURCE)).get();
        } catch (Exception ex) {
            throw new KafkaException(ex.getMessage(), ex);
        } finally {
            template.flush();
        }
    }

    private <T> ProducerRecord<String, T> createProcedureRecord(T dto, KafkaHeader header) {
        ProducerRecord<String, T> record = new ProducerRecord<>(template.getDefaultTopic(),
                UUID.randomUUID().toString(),
                dto);
        record.headers().add("X-TYPE", header.toString().getBytes());

        return record;
    }
}
