package ru.t1.java.demo.config;

import org.springframework.beans.factory.annotation.Value;

public class DefaultKafkaConfig {
    @Value("${t1.kafka.consumer.group-id}")
    String groupId;

    @Value("${t1.kafka.bootstrap.server}")
    protected String servers;

    @Value("${t1.kafka.session.timeout.ms:45000}")
    protected String sessionTimeout;

    @Value("${t1.kafka.max.partition.fetch.bytes:300000}")
    protected String maxPartitionFetchBytes;

    @Value("${t1.kafka.max.poll.records:1}")
    protected String maxPollRecords;

    @Value("${t1.kafka.max.poll.interval.ms:300000}")
    protected String maxPollIntervalsMs;

    @Value("${t1.kafka.consumer.heartbeat.interval}")
    protected String heartbeatInterval;

    @Value("${t1.kafka.topic.metric}")
    protected String metricTopic;

    @Value("${t1.kafka.topic.account}")
    protected String accountTopic;

    @Value("${t1.kafka.topic.transaction}")
    protected String transactionTopic;

    @Value("${t1.kafka.topic.client_id_registered}")
    protected String clientRegisteredTopic;
}
