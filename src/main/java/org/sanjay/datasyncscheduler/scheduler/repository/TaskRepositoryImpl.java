package org.sanjay.datasyncscheduler.scheduler.repository;

import org.sanjay.datasyncscheduler.adapter.destination.enums.DestinationType;
import org.sanjay.datasyncscheduler.adapter.source.enums.SourceType;
import org.sanjay.datasyncscheduler.sync.config.SyncTaskConfig;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class TaskRepositoryImpl implements TaskRepository {

    private final List<SyncTaskConfig> allTasks;

    TaskRepositoryImpl() {
        allTasks = new LinkedList<>();
        SyncTaskConfig syncTaskConfig1 = new SyncTaskConfig(1, SourceType.AWS_S3, DestinationType.Local_File_System, "skatla-rudderstack-1");
        allTasks.add(syncTaskConfig1);
    }

    @Override
    public List<SyncTaskConfig> getAllTasks() {
        return allTasks;
    }
}
