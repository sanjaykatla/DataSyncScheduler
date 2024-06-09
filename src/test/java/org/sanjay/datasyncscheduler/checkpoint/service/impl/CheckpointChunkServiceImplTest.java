package org.sanjay.datasyncscheduler.checkpoint.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.sanjay.datasyncscheduler.checkpoint.model.CheckpointChunk;
import org.sanjay.datasyncscheduler.checkpoint.repository.CheckpointChunkRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CheckpointChunkServiceImplTest {

    @Mock
    private CheckpointChunkRepository checkpointChunkRepository;

    @InjectMocks
    private CheckpointChunkServiceImpl checkpointChunkService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveCheckpointChunk() {
        when(checkpointChunkRepository.save(any(CheckpointChunk.class))).thenReturn(new CheckpointChunk());

        checkpointChunkService.saveCheckpointChunk("test-bucket", "test-key", 1024L);

        verify(checkpointChunkRepository, times(1)).save(any(CheckpointChunk.class));
    }

    @Test
    void testGetLastProcessedByteFound() {
        CheckpointChunk checkpointChunk = new CheckpointChunk();
        checkpointChunk.setBucketName("test-bucket");
        checkpointChunk.setKey("test-key");
        checkpointChunk.setLastProcessedByte(1024L);

        when(checkpointChunkRepository.findByBucketNameAndKey("test-bucket", "test-key")).thenReturn(checkpointChunk);

        long lastProcessedByte = checkpointChunkService.getLastProcessedByte("test-bucket", "test-key");

        assertEquals(1024L, lastProcessedByte);
    }

    @Test
    void testGetLastProcessedByteNotFound() {
        when(checkpointChunkRepository.findByBucketNameAndKey("test-bucket", "test-key")).thenReturn(null);

        long lastProcessedByte = checkpointChunkService.getLastProcessedByte("test-bucket", "test-key");

        assertEquals(0L, lastProcessedByte);
    }

    @Test
    void testDeleteCheckpointChunkFound() {
        CheckpointChunk checkpointChunk = new CheckpointChunk();
        checkpointChunk.setBucketName("test-bucket");
        checkpointChunk.setKey("test-key");

        when(checkpointChunkRepository.findByBucketNameAndKey("test-bucket", "test-key")).thenReturn(checkpointChunk);
        doNothing().when(checkpointChunkRepository).delete(checkpointChunk);

        checkpointChunkService.deleteCheckpointChunk("test-bucket", "test-key");

        verify(checkpointChunkRepository, times(1)).findByBucketNameAndKey("test-bucket", "test-key");
        verify(checkpointChunkRepository, times(1)).delete(checkpointChunk);
    }

    @Test
    void testDeleteCheckpointChunkNotFound() {
        when(checkpointChunkRepository.findByBucketNameAndKey("test-bucket", "test-key")).thenReturn(null);

        checkpointChunkService.deleteCheckpointChunk("test-bucket", "test-key");

        verify(checkpointChunkRepository, times(1)).findByBucketNameAndKey("test-bucket", "test-key");
        verify(checkpointChunkRepository, never()).delete(any(CheckpointChunk.class));
    }
}
