package com.artgr.montyhall.properties;

import lombok.Data;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("simulator")
public class SimulatorProperties {
    private int iterations = 10;
}
