package ru.t1.java.demo.kafka;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import ru.t1.java.demo.config.DefaultKafkaConfig;
import ru.t1.java.demo.dto.transaction.TransactionAcceptDto;
import ru.t1.java.demo.dto.transaction.TransactionResultDto;
import ru.t1.java.demo.exception.KafkaException;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class KafkaTransactionProducer {
    private final KafkaTemplate template;
    private final DefaultKafkaConfig config;

    public void send(TransactionResultDto transactionResultDto) {
        sendTemplate(config.getTransactionResultTopic(), transactionResultDto);
    }

    private <T> void sendTemplate(String topic, T dto) {
        try {
            template.send(new ProducerRecord<>(topic, UUID.randomUUID().toString(), dto)).get();
        } catch (Exception ex) {
            throw new KafkaException(ex.getMessage(), ex);
        } finally {
            template.flush();
        }
    }
}
