package org.sanjay.datasyncscheduler.scheduler.repository;

import org.sanjay.datasyncscheduler.sync.config.TaskConfiguration;

import java.util.List;

public interface TaskRepository {

    List<TaskConfiguration> getAllTasks();
}
