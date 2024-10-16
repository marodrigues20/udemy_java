package com.course.kafka.section_09.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Section 09. 46. Scheduling Consumer
 */
@Service
public class GeneralLedgerScheduler {

    private KafkaListenerEndpointRegistry registry;

    private static final Logger LOG = LoggerFactory.getLogger(GeneralLedgerScheduler.class);

    @Scheduled(cron = "0 7 7 * * ?")
    public void stop(){
        LOG.info("Stopping consumer");
        registry.getListenerContainer("general-ledger.one").pause();
    }

    @Scheduled(cron = "1 9 7 * * ?")
    public void start(){
        LOG.info("Starting consumer");
        registry.getListenerContainer("general-ledger.one").start();
    }

}
