package ru.t1.java.demo.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Component
@ConfigurationProperties
public class DefaultKafkaConfig {
    @Value("${t1.kafka.consumer.group-id}")
    private String groupId;

    @Value("${t1.kafka.bootstrap.server}")
    private String servers;

    @Value("${t1.kafka.session.timeout.ms:45000}")
    private String sessionTimeout;

    @Value("${t1.kafka.max.partition.fetch.bytes:300000}")
    private String maxPartitionFetchBytes;

    @Value("${t1.kafka.max.poll.records:1}")
    private String maxPollRecords;

    @Value("${t1.kafka.max.poll.interval.ms:300000}")
    private String maxPollIntervalsMs;

    @Value("${t1.kafka.consumer.heartbeat.interval}")
    private String heartbeatInterval;

    @Value("${t1.kafka.topic.metric}")
    private String metricTopic;

    @Value("${t1.kafka.topic.account}")
    private String accountTopic;

    @Value("${t1.kafka.topic.transactions.default}")
    private String transactionTopic;

    @Value("${t1.kafka.topic.transactions.accepted}")
    private String transactionAcceptedTopic;

    @Value("${t1.kafka.topic.transactions.result}")
    private String transactionResultTopic;

    @Value("${t1.kafka.topic.client_id_registered}")
    private String clientRegisteredTopic;

    @Value("${t1.kafka.limit.transactionCountPerTime:5")
    private int transactionCountPerTime;

    @Value("${t1.kafka.limit.transactionTimeIntervalMs:10000")
    private int transactionTimeIntervalMs;
}
