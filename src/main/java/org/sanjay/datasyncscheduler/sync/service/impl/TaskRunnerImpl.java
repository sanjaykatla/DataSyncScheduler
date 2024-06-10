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
import org.sanjay.datasyncscheduler.checkpoint.service.CheckpointChunkService;
import org.sanjay.datasyncscheduler.checkpoint.service.CheckpointService;
import org.sanjay.datasyncscheduler.model.SyncObject;
import org.sanjay.datasyncscheduler.sync.config.TaskConfiguration;
import org.sanjay.datasyncscheduler.sync.service.TaskRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TaskRunnerImpl implements TaskRunner {

    private static final Logger logger = LoggerFactory.getLogger(TaskRunnerImpl.class);

    private final SourceFactory sourceFactory;

    private final DestinationFactory destinationFactory;

    private final CheckpointService checkpointService;

    private final CheckpointChunkService checkpointChunkService;

    public void run(TaskConfiguration taskConfiguration, String bucketName, SyncObject syncObject) {
        SourceType sourceType = taskConfiguration.getSourceType();
        DestinationType destinationType = taskConfiguration.getDestinationType();

        logger.info("Running Task : {}", taskConfiguration.getId());
        SourceStorageService sourceService = sourceFactory.getSourceStorageService(sourceType);
        DestinationStorageService destinationService = destinationFactory.getDestinationStorageService(destinationType);

        try {
            String key = syncObject.getKey();
            Long size = syncObject.getSize();
            long lastProcessedByte = checkpointChunkService.getLastProcessedByte(bucketName, key);
            for (long start = lastProcessedByte, chunkNumber = 0; start < size; start += taskConfiguration.getChunkSize(), chunkNumber++) {
                logger.info("Downloading chunk {} for bucket: {} for object: {}", chunkNumber, bucketName, key);
                long end = Math.min(start + taskConfiguration.getChunkSize(), size);

                byte[] data = getDataFromSourceWithRetry(bucketName, sourceService, key, start, end, chunkNumber, taskConfiguration.getMaxRetries());
                saveToDestinationWithRetry(bucketName, destinationService, key, data, chunkNumber, taskConfiguration.getMaxRetries(), end);

                logger.info("Uploaded chunk {} for bucket: {} for object: {}", chunkNumber, bucketName, key);
            }
            checkpointService.upsertCheckpoint(bucketName, key, LocalDateTime.now().getNano());
            logger.info("Task : {} completed for bucket: {} for object: {}", taskConfiguration.getId(), bucketName, key);
        } catch (SourceServiceException | SaveFailedException | InvalidSourceKeyNameException |
                 InvalidSourceObjectStateException | SourceException | SourceSdkClientException e) {
            throw new RuntimeException(e);
        }
    }

    private static byte[] getDataFromSourceWithRetry(String bucketName, SourceStorageService sourceService, String key, long start, long end, long chunkNumber, int maxRetries) throws SourceServiceException, InvalidSourceKeyNameException, InvalidSourceObjectStateException, SourceException, SourceSdkClientException {
        byte[] data = null;
        for (int attempt = 1; attempt <= maxRetries; attempt++) {
            try {
                data = sourceService.getObjectAsBytes(bucketName, key, start, end);
                break;
            } catch (SourceServiceException | InvalidSourceKeyNameException | InvalidSourceObjectStateException |
                     SourceException | SourceSdkClientException e) {
                if (attempt == maxRetries) {
                    logger.error("Failed to download chunk {} after {} attempts", chunkNumber, maxRetries);
                    throw e;
                }
                logger.warn("Retrying download chunk {} attempt {} of {}", chunkNumber, attempt, maxRetries);
            }
        }
        return data;
    }

    private void saveToDestinationWithRetry(String bucketName, DestinationStorageService destinationService, String key, byte[] data, long chunkNumber, int maxRetries, long lastProcessedByte) throws SaveFailedException {
        for (int attempt = 1; attempt <= maxRetries; attempt++) {
            try {
                destinationService.putObject(bucketName, key, data);
                checkpointChunkService.saveCheckpointChunk(bucketName, key, lastProcessedByte);
                logger.info("Uploaded chunk {} for bucket: {} for object: {}", chunkNumber, bucketName, key);
                break;
            } catch (SaveFailedException e) {
                if (attempt == maxRetries) {
                    logger.error("Failed to upload chunk {} after {} attempts", chunkNumber, maxRetries);
                    throw e;
                }
                logger.warn("Retrying upload chunk {} attempt {} of {}", chunkNumber, attempt, maxRetries);
            }
        }
    }
}