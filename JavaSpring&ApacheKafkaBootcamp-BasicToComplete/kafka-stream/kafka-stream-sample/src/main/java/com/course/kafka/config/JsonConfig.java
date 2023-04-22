package com.course.kafka.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Section 13: 77. First Step - Commodity Stream
 */
@Configuration
public class JsonConfig {

    /**
     * create an instance of object mapper, and register modules.
     * This will add the jackson library we added earlier to recognize date data type.
     * Return it.
     */
    @Bean
    public ObjectMapper objectMapper(){
        var objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        // By default, jackson will not write LocalDate as String. If you want it as String, add the bellow line
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        return objectMapper;
    }
}
