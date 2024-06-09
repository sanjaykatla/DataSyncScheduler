package org.sanjay.datasyncscheduler.sync.service;

import org.sanjay.datasyncscheduler.sync.config.SyncTaskConfig;

public interface TaskRunner {

    void run(
            SyncTaskConfig syncTaskConfig,
            String bucketName,
            String key
    );
}
