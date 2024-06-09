package org.sanjay.datasyncscheduler.checkpoint.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.sanjay.datasyncscheduler.checkpoint.model.CheckpointChunk;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class CheckpointChunkRepositoryTest {

    @Autowired
    private CheckpointChunkRepository checkpointChunkRepository;

    @BeforeEach
    void setUp() {
        checkpointChunkRepository.deleteAll();
    }

    @Test
    void testSaveAndFindByBucketNameAndKey() {
        CheckpointChunk checkpointChunk = new CheckpointChunk();
        checkpointChunk.setBucketName("test-bucket");
        checkpointChunk.setKey("test-key");
        checkpointChunk.setLastProcessedByte(12345L);

        checkpointChunkRepository.save(checkpointChunk);

        CheckpointChunk found = checkpointChunkRepository.findByBucketNameAndKey("test-bucket", "test-key");
        assertNotNull(found);
        assertEquals("test-bucket", found.getBucketName());
        assertEquals("test-key", found.getKey());
        assertEquals(12345L, found.getLastProcessedByte());
    }

    @Test
    void testSaveAndFindById() {
        CheckpointChunk checkpointChunk = new CheckpointChunk();
        checkpointChunk.setBucketName("test-bucket");
        checkpointChunk.setKey("test-key");
        checkpointChunk.setLastProcessedByte(12345L);

        CheckpointChunk saved = checkpointChunkRepository.save(checkpointChunk);
        Optional<CheckpointChunk> found = checkpointChunkRepository.findById(saved.getId());
        assertTrue(found.isPresent());
        assertEquals(saved.getId(), found.get().getId());
    }

    @Test
    void testDelete() {
        CheckpointChunk checkpointChunk = new CheckpointChunk();
        checkpointChunk.setBucketName("test-bucket");
        checkpointChunk.setKey("test-key");
        checkpointChunk.setLastProcessedByte(12345L);

        CheckpointChunk saved = checkpointChunkRepository.save(checkpointChunk);
        checkpointChunkRepository.deleteById(saved.getId());

        Optional<CheckpointChunk> found = checkpointChunkRepository.findById(saved.getId());
        assertFalse(found.isPresent());
    }
}
