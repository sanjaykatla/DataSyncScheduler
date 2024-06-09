package org.sanjay.datasyncscheduler.scheduler.repository;

import org.sanjay.datasyncscheduler.sync.config.SyncTaskConfig;

import java.util.List;

public interface TaskRepository {

    List<SyncTaskConfig> getAllTasks();
}
