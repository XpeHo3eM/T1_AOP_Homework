package ru.t1.java.starter;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.t1.java.starter.aop.HttpLogger;

@Configuration
@EnableConfigurationProperties(Properties.class)
@RequiredArgsConstructor
public class Starter {
    private final Properties properties;

    @Bean
    @ConditionalOnProperty(name = "log.enable", havingValue = "true", matchIfMissing = false)
    public HttpLogger createLogger() {
        return new HttpLogger(properties.getType());
    }
}
