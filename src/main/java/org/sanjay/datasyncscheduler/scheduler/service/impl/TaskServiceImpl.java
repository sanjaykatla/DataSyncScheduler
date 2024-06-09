package org.sanjay.datasyncscheduler.scheduler.service.impl;

import org.sanjay.datasyncscheduler.scheduler.Scheduler;
import org.sanjay.datasyncscheduler.scheduler.repository.TaskRepository;
import org.sanjay.datasyncscheduler.scheduler.service.TaskService;
import org.sanjay.datasyncscheduler.sync.config.TaskConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    private static final Logger logger = LoggerFactory.getLogger(Scheduler.class);
    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public List<TaskConfiguration> getAllTasks() {
        logger.info("Fetching all tasks");
        return taskRepository.getAllTasks();
    }
}
