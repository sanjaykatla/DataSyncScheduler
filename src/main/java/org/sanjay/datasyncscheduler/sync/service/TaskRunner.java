package org.sanjay.datasyncscheduler.sync.service;

import org.sanjay.datasyncscheduler.sync.config.TaskConfiguration;

public interface TaskRunner {

    void run(
            TaskConfiguration taskConfiguration,
            String bucketName,
            String key
    );
}
