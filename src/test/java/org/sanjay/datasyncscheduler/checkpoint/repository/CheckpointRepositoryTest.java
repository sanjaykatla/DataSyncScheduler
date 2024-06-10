package org.sanjay.datasyncscheduler.checkpoint.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.sanjay.datasyncscheduler.checkpoint.model.Checkpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class CheckpointRepositoryTest {

    @Autowired
    private CheckpointRepository checkpointRepository;

    @BeforeEach
    void setUp() {
        checkpointRepository.deleteAll();
    }

    @Test
    void testSaveAndFindByBucketNameAndKey() {
        Checkpoint checkpoint = new Checkpoint();
        checkpoint.setBucketName("test-bucket");
        checkpoint.setObjectName("test-key");
        checkpoint.setLastSuccessfulSyncTime(12345L);

        checkpointRepository.save(checkpoint);

        Checkpoint found = checkpointRepository.findByBucketNameAndKey("test-bucket", "test-key");
        assertNotNull(found);
        assertEquals("test-bucket", found.getBucketName());
        assertEquals("test-key", found.getObjectName());
        assertEquals(12345L, found.getLastSuccessfulSyncTime());
    }

    @Test
    void testSaveAndFindById() {
        Checkpoint checkpoint = new Checkpoint();
        checkpoint.setBucketName("test-bucket");
        checkpoint.setObjectName("test-key");
        checkpoint.setLastSuccessfulSyncTime(0L);

        Checkpoint saved = checkpointRepository.save(checkpoint);
        Optional<Checkpoint> found = checkpointRepository.findById(saved.getId());
        assertTrue(found.isPresent());
        assertEquals(saved.getId(), found.get().getId());
    }

    @Test
    void testDelete() {
        Checkpoint checkpoint = new Checkpoint();
        checkpoint.setBucketName("test-bucket");
        checkpoint.setObjectName("test-key");
        checkpoint.setLastSuccessfulSyncTime(12345L);

        Checkpoint saved = checkpointRepository.save(checkpoint);
        checkpointRepository.deleteById(saved.getId());

        Optional<Checkpoint> found = checkpointRepository.findById(saved.getId());
        assertFalse(found.isPresent());
    }
}

