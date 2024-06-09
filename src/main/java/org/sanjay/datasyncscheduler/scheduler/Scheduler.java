package org.sanjay.datasyncscheduler.scheduler;

import lombok.RequiredArgsConstructor;
import org.sanjay.datasyncscheduler.adapter.destination.enums.DestinationType;
import org.sanjay.datasyncscheduler.adapter.source.enums.SourceType;
import org.sanjay.datasyncscheduler.adapter.source.exception.InvalidSourceKeyNameException;
import org.sanjay.datasyncscheduler.adapter.source.exception.SourceException;
import org.sanjay.datasyncscheduler.adapter.source.exception.SourceSdkClientException;
import org.sanjay.datasyncscheduler.adapter.source.exception.SourceServiceException;
import org.sanjay.datasyncscheduler.sync.config.SyncTaskConfig;
import org.sanjay.datasyncscheduler.sync.service.TaskSplitter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Scheduler {

    private static final Logger logger = LoggerFactory.getLogger(Scheduler.class);

    private final TaskSplitter taskSplitter;

    public Scheduler(TaskSplitter taskSplitter) {
        this.taskSplitter = taskSplitter;
    }

    @Scheduled(cron = "0 */1 * * * *") // Runs every 2 minutes
    public void scheduleSyncJob() throws SourceServiceException, InvalidSourceKeyNameException, SourceException, SourceSdkClientException {

        logger.info("task submitted");
        String bucketName = "skatla-rudderstack-1";
        SyncTaskConfig syncTaskConfig = new SyncTaskConfig(SourceType.Invalid, DestinationType.Local_File_System);
        taskSplitter.splitAndSubmit(syncTaskConfig, bucketName);
    }
}
