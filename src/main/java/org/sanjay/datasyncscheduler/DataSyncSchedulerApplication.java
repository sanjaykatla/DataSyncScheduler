package org.sanjay.datasyncscheduler;

import lombok.RequiredArgsConstructor;
import org.sanjay.datasyncscheduler.sync.service.TaskSplitter;
import org.sanjay.datasyncscheduler.sync.service.impl.TaskSplitterImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DataSyncSchedulerApplication {

    private static final Logger logger = LoggerFactory.getLogger(DataSyncSchedulerApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(DataSyncSchedulerApplication.class, args);
        logger.info("Data Sync Scheduler Application Started");
    }

}
