package org.sanjay.datasyncscheduler.sync.service.impl;

import lombok.RequiredArgsConstructor;
import org.sanjay.datasyncscheduler.adapter.source.exception.InvalidSourceKeyNameException;
import org.sanjay.datasyncscheduler.adapter.source.exception.SourceException;
import org.sanjay.datasyncscheduler.adapter.source.exception.SourceSdkClientException;
import org.sanjay.datasyncscheduler.adapter.source.exception.SourceServiceException;
import org.sanjay.datasyncscheduler.adapter.source.factory.SourceFactory;
import org.sanjay.datasyncscheduler.adapter.source.service.SourceStorageService;
import org.sanjay.datasyncscheduler.sync.config.SyncTaskConfig;
import org.sanjay.datasyncscheduler.sync.service.TaskRunner;
import org.sanjay.datasyncscheduler.sync.service.TaskSplitter;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskSplitterImpl implements TaskSplitter {

    private final SourceFactory sourceFactory;

    private final TaskRunner taskRunner;

    @Override
    public void splitAndSubmit(SyncTaskConfig syncTaskConfig, String bucketName) throws SourceServiceException, InvalidSourceKeyNameException, SourceException, SourceSdkClientException {

        SourceStorageService sourceStorageService = sourceFactory.getSourceStorageService(syncTaskConfig.getSourceType());
        sourceStorageService.listObjects(bucketName)
                .forEach(objectKey -> taskRunner.run(syncTaskConfig, bucketName, objectKey));

    }
}
