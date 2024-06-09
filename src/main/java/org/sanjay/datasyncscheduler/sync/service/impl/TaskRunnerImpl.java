package org.sanjay.datasyncscheduler.sync.service.impl;

import lombok.RequiredArgsConstructor;
import org.sanjay.datasyncscheduler.adapter.destination.enums.DestinationType;
import org.sanjay.datasyncscheduler.adapter.destination.exception.SaveFailedException;
import org.sanjay.datasyncscheduler.adapter.destination.factory.DestinationFactory;
import org.sanjay.datasyncscheduler.adapter.destination.service.DestinationStorageService;
import org.sanjay.datasyncscheduler.adapter.source.enums.SourceType;
import org.sanjay.datasyncscheduler.adapter.source.exception.*;
import org.sanjay.datasyncscheduler.adapter.source.factory.SourceFactory;
import org.sanjay.datasyncscheduler.adapter.source.service.SourceStorageService;
import org.sanjay.datasyncscheduler.sync.config.TaskConfiguration;
import org.sanjay.datasyncscheduler.sync.service.TaskRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskRunnerImpl implements TaskRunner {

    private static final Logger logger = LoggerFactory.getLogger(TaskRunnerImpl.class);

    private final SourceFactory sourceFactory;

    private final DestinationFactory destinationFactory;

    public void run(TaskConfiguration taskConfiguration, String bucketName, String key) {
        SourceType sourceType = taskConfiguration.getSourceType();
        DestinationType destinationType = taskConfiguration.getDestinationType();

        logger.info("Running Task : {}", taskConfiguration.getId());
        SourceStorageService sourceService = sourceFactory.getSourceStorageService(sourceType);
        DestinationStorageService destinationService = destinationFactory.getDestinationStorageService(destinationType);

        try {
            byte[] data = sourceService.getObjectAsBytes(bucketName, key);
            destinationService.putObject(bucketName, key, data);
            logger.info("Task : {} completed for bucket: {} for object: {}", taskConfiguration.getId(), bucketName, key);
        } catch (SourceServiceException | SaveFailedException | InvalidSourceKeyNameException |
                 InvalidSourceObjectStateException | SourceException | SourceSdkClientException e) {
            throw new RuntimeException(e);
        }
    }
}