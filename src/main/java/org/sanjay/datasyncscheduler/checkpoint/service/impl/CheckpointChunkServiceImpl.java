package org.sanjay.datasyncscheduler.checkpoint.service.impl;

import org.sanjay.datasyncscheduler.checkpoint.model.CheckpointChunk;
import org.sanjay.datasyncscheduler.checkpoint.repository.CheckpointChunkRepository;
import org.sanjay.datasyncscheduler.checkpoint.service.CheckpointChunkService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CheckpointChunkServiceImpl implements CheckpointChunkService {

    private static final Logger logger = LoggerFactory.getLogger(CheckpointChunkServiceImpl.class);
    private final CheckpointChunkRepository checkpointChunkRepository;

    public CheckpointChunkServiceImpl(CheckpointChunkRepository checkpointChunkRepository) {
        this.checkpointChunkRepository = checkpointChunkRepository;
    }

    @Override
    public void saveCheckpointChunk(String bucketName, String key, long lastProcessedByte) {

        CheckpointChunk checkpointChunk = new CheckpointChunk();
        checkpointChunk.setBucketName(bucketName);
        checkpointChunk.setKey(key);
        checkpointChunk.setLastProcessedByte(lastProcessedByte);
        checkpointChunkRepository.save(checkpointChunk);
        logger.info("CheckpointChunk saved successfully for bucketName: {} and key: {}", bucketName, key);

    }

    @Override
    public long getLastProcessedByte(String bucketName, String key) {
        CheckpointChunk checkpointChunk = checkpointChunkRepository.findByBucketNameAndKey(bucketName, key);
        if (checkpointChunk != null) {
            logger.info("CheckpointChunk found for bucketName: {} and key: {} and lastProcessedByte: {}", bucketName, key, checkpointChunk.getLastProcessedByte());
            return checkpointChunk.getLastProcessedByte();
        }
        logger.info("CheckpointChunk not found for bucketName: {} and key: {}", bucketName, key);
        return 0;
    }

    @Override
    public void deleteCheckpointChunk(String bucketName, String key) {

        CheckpointChunk checkpointChunk = checkpointChunkRepository.findByBucketNameAndKey(bucketName, key);
        if (checkpointChunk != null) {
            logger.info("Deleting CheckpointChunk for bucketName: {} and key: {}", bucketName, key);
            checkpointChunkRepository.delete(checkpointChunk);
        } else {
            logger.info("CheckpointChunk not found for bucketName: {} and key: {}", bucketName, key);
        }
    }
}
