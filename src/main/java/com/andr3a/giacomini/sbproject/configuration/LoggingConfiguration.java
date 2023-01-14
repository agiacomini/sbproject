package com.andr3a.giacomini.sbproject.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoggingConfiguration {
    @Bean
    public Logger log() {
        var log = LoggerFactory.getLogger("com.andr3a.giacomini.sbproject");
        return log;
    }
}