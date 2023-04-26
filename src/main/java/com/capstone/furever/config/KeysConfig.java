package com.capstone.furever.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeysConfig {
    @Value("${keys.filestack}")
    public String fileStack;
    @Value("${keys.mapbox}")
    public String mapbox;
    @Value("tp21z3C9")
    public String talkjs;
}
