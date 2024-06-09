package org.sanjay.datasyncscheduler.scheduler;

import org.sanjay.datasyncscheduler.adapter.source.exception.InvalidSourceKeyNameException;
import org.sanjay.datasyncscheduler.adapter.source.exception.SourceException;
import org.sanjay.datasyncscheduler.adapter.source.exception.SourceSdkClientException;
import org.sanjay.datasyncscheduler.adapter.source.exception.SourceServiceException;
import org.sanjay.datasyncscheduler.scheduler.service.TaskService;
import org.sanjay.datasyncscheduler.sync.config.TaskConfiguration;
import org.sanjay.datasyncscheduler.sync.service.TaskSplitter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Scheduler {

    private static final Logger logger = LoggerFactory.getLogger(Scheduler.class);

    private final TaskSplitter taskSplitter;
    private final TaskService taskService;

    public Scheduler(TaskSplitter taskSplitter, TaskService taskService) {
        this.taskSplitter = taskSplitter;
        this.taskService = taskService;
    }

    @Scheduled(cron = "0 */1 * * * *") // Runs every 2 minutes
    public void scheduleSyncJobs() throws SourceServiceException, InvalidSourceKeyNameException, SourceException, SourceSdkClientException {

        logger.info("Scheduler Started");
        List<TaskConfiguration> allTasks = taskService.getAllTasks();
        for(TaskConfiguration task : allTasks) {
            logger.info("Splitting Task : {}", task.getId());
            taskSplitter.splitAndSubmit(task);
        }
    }
}
