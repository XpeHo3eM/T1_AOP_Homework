package ru.t1.java.demo.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.util.backoff.FixedBackOff;
import ru.t1.java.demo.dto.account.NewAccountDto;
import ru.t1.java.demo.dto.client.ClientDto;
import ru.t1.java.demo.dto.dataSourceErrorLog.NewDataSourceErrorLogDto;
import ru.t1.java.demo.dto.metric.NewMetricDto;
import ru.t1.java.demo.dto.transaction.NewTransactionDto;
import ru.t1.java.demo.dto.TransactionAcceptDto;
import ru.t1.java.demo.kafka.KafkaClientProducer;
import ru.t1.java.demo.kafka.KafkaDataSourceExceptionProducer;
import ru.t1.java.demo.kafka.KafkaMetricProducer;
import ru.t1.java.demo.kafka.KafkaTransactionProducer;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class KafkaConfig<T> {
    private final DefaultKafkaConfig config;

    @Bean
    public ConsumerFactory<String, ClientDto> consumerListenerFactory() {
        Map<String, Object> props = generateDefaultProps(ClientDto.class);

        DefaultKafkaConsumerFactory factory = new DefaultKafkaConsumerFactory<String, ClientDto>(props);
        factory.setKeyDeserializer(new StringDeserializer());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, NewAccountDto> consumerAccountListenerFactory() {
        Map<String, Object> props = generateDefaultProps(NewAccountDto.class);

        DefaultKafkaConsumerFactory factory = new DefaultKafkaConsumerFactory<String, NewAccountDto>(props);
        factory.setKeyDeserializer(new StringDeserializer());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, NewTransactionDto> consumerTransactionListenerFactory() {
        Map<String, Object> props = generateDefaultProps(NewTransactionDto.class);

        DefaultKafkaConsumerFactory factory = new DefaultKafkaConsumerFactory<String, NewTransactionDto>(props);
        factory.setKeyDeserializer(new StringDeserializer());
        return factory;
    }

    @Bean
    ConcurrentKafkaListenerContainerFactory<String, ClientDto> kafkaListenerContainerFactory(@Qualifier("consumerListenerFactory") ConsumerFactory<String, ClientDto> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, ClientDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factoryBuilder(consumerFactory, factory);
        return factory;
    }

    @Bean
    ConcurrentKafkaListenerContainerFactory<String, NewAccountDto> kafkaAccountListenerContainerFactory(
            @Qualifier("consumerAccountListenerFactory") ConsumerFactory<String, NewAccountDto> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, NewAccountDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factoryBuilder(consumerFactory, factory);

        return factory;
    }

    @Bean
    ConcurrentKafkaListenerContainerFactory<String, NewTransactionDto> kafkaTransactionListenerContainerFactory(
            @Qualifier("consumerTransactionListenerFactory") ConsumerFactory<String, NewTransactionDto> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, NewTransactionDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factoryBuilder(consumerFactory, factory);

        return factory;
    }

    @Bean("client")
    @Primary
    public KafkaTemplate<String, T> kafkaClientTemplate(@Qualifier("producerClientFactory") ProducerFactory<String, T> producerPatFactory) {
        return new KafkaTemplate<>(producerPatFactory);
    }

    @Bean
    @ConditionalOnProperty(value = "t1.kafka.producer.enable",
            havingValue = "true",
            matchIfMissing = true)
    public KafkaClientProducer producerClient(@Qualifier("client") KafkaTemplate<String, ClientDto> template) {
        template.setDefaultTopic(config.getClientRegisteredTopic());

        return new KafkaClientProducer(template);
    }

    @Bean
    @ConditionalOnProperty(value = "t1.kafka.producer.metric-enable",
            havingValue = "true",
            matchIfMissing = true)
    public KafkaMetricProducer metricProducer(KafkaTemplate<String, NewMetricDto> template) {
        template.setDefaultTopic(config.getMetricTopic());

        return new KafkaMetricProducer(template);
    }

    @Bean
    @ConditionalOnProperty(value = "t1.kafka.producer.data-source-error-log-enable",
            havingValue = "true",
            matchIfMissing = true)
    public KafkaDataSourceExceptionProducer dataSourceExceptionProducer(KafkaTemplate<String, NewDataSourceErrorLogDto> template) {
        template.setDefaultTopic(config.getMetricTopic());

        return new KafkaDataSourceExceptionProducer(template);
    }

    @Bean
    @ConditionalOnProperty(value = "t1.kafka.producer.transaction-enable",
            havingValue = "true",
            matchIfMissing = true)
    public KafkaTransactionProducer transactionProducer(KafkaTemplate<String, TransactionAcceptDto> template) {
        return new KafkaTransactionProducer(template, config);
    }


    @Bean("producerClientFactory")
    public ProducerFactory<String, T> producerClientFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, config.getServers());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        props.put(ProducerConfig.RETRIES_CONFIG, 3);
        props.put(ProducerConfig.RETRY_BACKOFF_MS_CONFIG, 1000);
        props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, false);
        return new DefaultKafkaProducerFactory<>(props);
    }

    private <T> Map<String, Object> generateDefaultProps(Class<T> tClass) {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, config.getServers());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, config.getGroupId());
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, tClass);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        props.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, config.getSessionTimeout());
        props.put(ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG, config.getMaxPartitionFetchBytes());
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, config.getMaxPollRecords());
        props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, config.getMaxPollIntervalsMs());
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, Boolean.FALSE);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, config.getHeartbeatInterval());

        props.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class.getName());
        props.put(ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS, JsonDeserializer.class);

        return props;
    }

    private <T> void factoryBuilder(ConsumerFactory<String, T> consumerFactory, ConcurrentKafkaListenerContainerFactory<String, T> factory) {
        factory.setConsumerFactory(consumerFactory);
        factory.setBatchListener(true);
        factory.setConcurrency(1);
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        factory.getContainerProperties().setPollTimeout(5000);
        factory.getContainerProperties().setMicrometerEnabled(true);
        factory.setCommonErrorHandler(errorHandler());
    }

    private CommonErrorHandler errorHandler() {
        DefaultErrorHandler handler =
                new DefaultErrorHandler(new FixedBackOff(1000, 3));
        handler.addNotRetryableExceptions(IllegalStateException.class);
        handler.setRetryListeners((record, ex, deliveryAttempt) -> {
            log.error(" RetryListeners message = {}, offset = {} deliveryAttempt = {}", ex.getMessage(), record.offset(), deliveryAttempt);
        });
        return handler;
    }
}
