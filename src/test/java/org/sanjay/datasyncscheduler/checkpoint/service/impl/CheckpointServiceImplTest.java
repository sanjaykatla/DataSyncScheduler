package org.sanjay.datasyncscheduler.checkpoint.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.sanjay.datasyncscheduler.checkpoint.model.Checkpoint;
import org.sanjay.datasyncscheduler.checkpoint.repository.CheckpointRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CheckpointServiceImplTest {

    @Mock
    private CheckpointRepository checkpointRepository;

    @InjectMocks
    private CheckpointServiceImpl checkpointService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveCheckpoint() {
        when(checkpointRepository.save(any(Checkpoint.class))).thenReturn(new Checkpoint());

        checkpointService.upsertCheckpoint("test-bucket", "test-key", 1625494321L);

        verify(checkpointRepository, times(1)).save(any(Checkpoint.class));
    }

    @Test
    void testGetLastSuccessfulSyncTimeFound() {
        Checkpoint checkpoint = new Checkpoint();
        checkpoint.setBucketName("test-bucket");
        checkpoint.setObjectName("test-key");
        checkpoint.setLastSuccessfulSyncTime(1625494321L);

        when(checkpointRepository.findByBucketNameAndObjectName("test-bucket", "test-key")).thenReturn(checkpoint);

        long lastSuccessfulSyncTime = checkpointService.getLastSuccessfulSyncTime("test-bucket", "test-key");

        assertEquals(1625494321L, lastSuccessfulSyncTime);
    }

    @Test
    void testGetLastSuccessfulSyncTimeNotFound() {
        when(checkpointRepository.findByBucketNameAndObjectName("test-bucket", "test-key")).thenReturn(null);

        long lastSuccessfulSyncTime = checkpointService.getLastSuccessfulSyncTime("test-bucket", "test-key");

        assertEquals(0L, lastSuccessfulSyncTime);
    }
}
