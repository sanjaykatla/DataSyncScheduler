package org.sanjay.datasyncscheduler.checkpoint.repository;

import org.sanjay.datasyncscheduler.checkpoint.model.CheckpointChunk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckpointChunkRepository extends JpaRepository<CheckpointChunk, Long> {

    CheckpointChunk findByBucketNameAndKey(String bucketName, String key);
}
