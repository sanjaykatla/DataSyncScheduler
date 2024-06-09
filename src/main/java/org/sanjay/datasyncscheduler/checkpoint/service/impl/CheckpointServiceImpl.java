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
    public void saveCheckpoint(String bucketName, String key, long lastSuccessfulSyncTime) {

        Checkpoint checkpoint = new Checkpoint();
        checkpoint.setBucketName(bucketName);
        checkpoint.setKey(key);
        checkpoint.setLastSuccessfulSyncTime(lastSuccessfulSyncTime);

        checkpointRepository.save(checkpoint);
        logger.info("Checkpoint saved successfully for bucketName: {} and key: {}", bucketName, key);

    }

    @Override
    public long getLastSuccessfulSyncTime(String bucketName, String key) {
        Checkpoint checkpoint = checkpointRepository.findByBucketNameAndKey(bucketName, key);
        if (checkpoint != null) {
            logger.info("Checkpoint found for bucketName: {} and key: {} and lastSuccessfulSyncTime: {}", bucketName, key, checkpoint.getLastSuccessfulSyncTime());
            return checkpoint.getLastSuccessfulSyncTime();
        }
        logger.info("Checkpoint not found for bucketName: {} and key: {}", bucketName, key);
        return 0;
    }
}
