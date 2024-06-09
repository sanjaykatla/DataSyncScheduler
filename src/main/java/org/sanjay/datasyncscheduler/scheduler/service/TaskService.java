package org.sanjay.datasyncscheduler.scheduler.service;

import org.sanjay.datasyncscheduler.sync.config.TaskConfiguration;

import java.util.List;

public interface TaskService {

    List<TaskConfiguration> getAllTasks();
}
