package com.course.kafka.section_7.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
31. Producing JSON Message
 Note: Just create a Bean ObjectMapper when you are just adding jackson without org.springframework.boot:spring-boot-starter-web library.
 When you use org.springframework.boot:spring-boot-starter-web library isn't required.
 In 34. Lecture this class is not required.
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
