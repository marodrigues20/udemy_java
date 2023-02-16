package com.course.kafka.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
31. Producing JSON Message
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

        return objectMapper;
    }



}
