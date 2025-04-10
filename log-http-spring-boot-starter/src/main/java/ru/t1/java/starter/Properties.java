package ru.t1.java.starter;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(value = "log")
@Getter
@Setter
public class Properties {
    private boolean enable;
    private String type;
}
