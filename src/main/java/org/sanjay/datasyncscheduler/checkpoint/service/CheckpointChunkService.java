package org.sanjay.datasyncscheduler.checkpoint.service;

public interface CheckpointChunkService {

    void saveCheckpointChunk(String bucketName, String key, long lastProcessedByte);

    long getLastProcessedByte(String bucketName, String key, long rangeStart, long rangeEnd);

    void deleteCheckpointChunk(String bucketName, String key);
}
