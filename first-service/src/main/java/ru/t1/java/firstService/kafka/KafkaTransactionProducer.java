package ru.t1.java.firstService.kafka;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import ru.t1.java.firstService.config.DefaultKafkaConfig;
import ru.t1.java.firstService.dto.transaction.NewTransactionDto;
import ru.t1.java.general.dto.transaction.TransactionAcceptDto;
import ru.t1.java.general.exception.KafkaException;

import java.util.UUID;

@RequiredArgsConstructor
public class KafkaTransactionProducer {
    private final KafkaTemplate template;
    private final DefaultKafkaConfig config;

    public void send(NewTransactionDto newTransactionDto) {
        sendTemplate(config.getTransactionTopic(), newTransactionDto);
    }

    public void send(TransactionAcceptDto transactionAcceptDto) {
        sendTemplate(config.getTransactionAcceptedTopic(), transactionAcceptDto);
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
