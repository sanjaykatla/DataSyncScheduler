package org.sanjay.datasyncscheduler.sync.service.impl;

import lombok.RequiredArgsConstructor;
import org.sanjay.datasyncscheduler.adapter.source.exception.InvalidSourceKeyNameException;
import org.sanjay.datasyncscheduler.adapter.source.exception.SourceException;
import org.sanjay.datasyncscheduler.adapter.source.exception.SourceSdkClientException;
import org.sanjay.datasyncscheduler.adapter.source.exception.SourceServiceException;
import org.sanjay.datasyncscheduler.adapter.source.factory.SourceFactory;
import org.sanjay.datasyncscheduler.adapter.source.service.SourceStorageService;
import org.sanjay.datasyncscheduler.checkpoint.service.CheckpointService;
import org.sanjay.datasyncscheduler.model.SyncObject;
import org.sanjay.datasyncscheduler.sync.config.TaskConfiguration;
import org.sanjay.datasyncscheduler.sync.service.TaskRunner;
import org.sanjay.datasyncscheduler.sync.service.TaskSplitter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskSplitterImpl implements TaskSplitter {

    private static final Logger logger = LoggerFactory.getLogger(TaskSplitterImpl.class);

    private final SourceFactory sourceFactory;
    private final TaskRunner taskRunner;
    private final CheckpointService checkpointService;


    @Override
    public void splitAndSubmit(TaskConfiguration taskConfiguration) throws SourceServiceException, InvalidSourceKeyNameException, SourceException, SourceSdkClientException {

        SourceStorageService sourceStorageService = sourceFactory.getSourceStorageService(taskConfiguration.getSourceType());
        String bucketName = taskConfiguration.getBucketName();
        sourceStorageService.listObjects(bucketName)
                .forEach(objectToSync -> {
                    if (!isObjectSyncRequired(bucketName, objectToSync.getKey(), objectToSync)) {
                        logger.info("Skipping Task: {} for bucket: {} for object: {} as it is not required to sync", taskConfiguration.getId(), bucketName, objectToSync.getKey());
                        return;
                    }
                    logger.info("Task: {} is Submitted for bucket: {} for object: {}", taskConfiguration.getId(), bucketName, objectToSync.getKey());
                    taskRunner.run(taskConfiguration, bucketName, objectToSync);
                });

    }

    private boolean isObjectSyncRequired(String bucketName, String key, SyncObject syncObject) {
        long lastSuccessfulSyncTime = checkpointService.getLastSuccessfulSyncTime(bucketName, key);
        return lastSuccessfulSyncTime == 0 || lastSuccessfulSyncTime < syncObject.getLastModified().getNano();
    }
}
