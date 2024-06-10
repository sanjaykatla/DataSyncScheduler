package org.sanjay.datasyncscheduler.checkpoint.service.impl;

import org.sanjay.datasyncscheduler.checkpoint.model.Checkpoint;
import org.sanjay.datasyncscheduler.checkpoint.repository.CheckpointRepository;
import org.sanjay.datasyncscheduler.checkpoint.service.CheckpointService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CheckpointServiceImpl implements CheckpointService {

    private static final Logger logger = LoggerFactory.getLogger(CheckpointServiceImpl.class);

    private final CheckpointRepository checkpointRepository;

    public CheckpointServiceImpl(CheckpointRepository checkpointRepository) {
        this.checkpointRepository = checkpointRepository;
    }

    @Override
    public void upsertCheckpoint(String bucketName, String objectName, long lastSuccessfulSyncTime) {
        Checkpoint checkpoint = checkpointRepository.findByBucketNameAndObjectName(bucketName, objectName);
        if (checkpoint == null) {
            checkpoint = new Checkpoint();
            checkpoint.setBucketName(bucketName);
            checkpoint.setObjectName(objectName);
        }
        checkpoint.setLastSuccessfulSyncTime(lastSuccessfulSyncTime);
        checkpointRepository.save(checkpoint);
        logger.info("Checkpoint upserted successfully for bucketName: {} and objectName: {}", bucketName, objectName);
    }

    @Override
    public long getLastSuccessfulSyncTime(String bucketName, String objectName) {
        Checkpoint checkpoint = checkpointRepository.findByBucketNameAndObjectName(bucketName, objectName);
        if (checkpoint != null) {
            logger.info("Checkpoint found for bucketName: {} and objectName: {} and lastSuccessfulSyncTime: {}", bucketName, objectName, checkpoint.getLastSuccessfulSyncTime());
            return checkpoint.getLastSuccessfulSyncTime();
        }
        logger.info("Checkpoint not found for bucketName: {} and objectName: {}", bucketName, objectName);
        return 0;
    }
}
