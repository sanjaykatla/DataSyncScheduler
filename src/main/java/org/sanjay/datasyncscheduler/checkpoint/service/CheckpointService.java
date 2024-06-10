package org.sanjay.datasyncscheduler.checkpoint.service;

public interface CheckpointService {

    void upsertCheckpoint(String bucketName, String key, long lastSuccessfulSyncTime);

    long getLastSuccessfulSyncTime(String bucketName, String key);
}
