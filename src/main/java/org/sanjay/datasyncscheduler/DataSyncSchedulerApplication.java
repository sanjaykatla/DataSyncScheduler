package org.sanjay.datasyncscheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DataSyncSchedulerApplication {

    private static final Logger logger = LoggerFactory.getLogger(DataSyncSchedulerApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(DataSyncSchedulerApplication.class, args);
        logger.info("Data Sync Scheduler Application Started");
        logger.error("Data Sync Scheduler Application Started");
    }

}
