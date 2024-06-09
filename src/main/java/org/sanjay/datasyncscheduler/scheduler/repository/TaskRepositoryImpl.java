package org.sanjay.datasyncscheduler.scheduler.repository;

import org.sanjay.datasyncscheduler.adapter.destination.enums.DestinationType;
import org.sanjay.datasyncscheduler.adapter.source.enums.SourceType;
import org.sanjay.datasyncscheduler.sync.config.TaskConfiguration;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class TaskRepositoryImpl implements TaskRepository {

    private final List<TaskConfiguration> allTasks;

    TaskRepositoryImpl() {
        allTasks = new LinkedList<>();
        TaskConfiguration syncTaskConfig1 = new TaskConfiguration(1, SourceType.AWS_S3, DestinationType.Local_File_System, "skatla-rudderstack-1");
        allTasks.add(syncTaskConfig1);
    }

    @Override
    public List<TaskConfiguration> getAllTasks() {
        return allTasks;
    }
}
