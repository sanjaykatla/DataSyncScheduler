package org.sanjay.datasyncscheduler.checkpoint.repository;

import org.sanjay.datasyncscheduler.checkpoint.model.Checkpoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckpointRepository extends JpaRepository<Checkpoint, Long> {

    Checkpoint findByBucketNameAndKey(String bucketName, String key);
}
