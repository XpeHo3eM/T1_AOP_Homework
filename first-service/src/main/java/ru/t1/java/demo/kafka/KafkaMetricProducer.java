package ru.t1.java.demo.kafka;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import ru.t1.java.demo.dto.metric.NewMetricDto;
import ru.t1.java.demo.kafka.enums.KafkaHeader;
import ru.t1.java.demo.exception.KafkaException;

import java.util.UUID;

@RequiredArgsConstructor
public class KafkaMetricProducer {
    private final KafkaTemplate template;

    public void send(NewMetricDto newMetricDto) {
        try {
            template.send(createProcedureRecord(newMetricDto, KafkaHeader.METRICS)).get();
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
