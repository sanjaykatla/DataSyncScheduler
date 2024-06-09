package org.sanjay.datasyncscheduler.scheduler.service;

import org.sanjay.datasyncscheduler.sync.config.SyncTaskConfig;

import java.util.List;

public interface TaskService {

    List<SyncTaskConfig> getAllTasks();
}
