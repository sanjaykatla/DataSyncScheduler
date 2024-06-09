package org.sanjay.datasyncscheduler.adapter.destination.local_file_system.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.sanjay.datasyncscheduler.adapter.destination.exception.SaveFailedException;
import org.sanjay.datasyncscheduler.adapter.destination.proxy.DestinationStorageProxy;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LocalFileSystemDestinationStorageServiceImplTest {

    @Mock
    private DestinationStorageProxy destinationStorageProxy;

    @InjectMocks
    private LocalFileSystemDestinationStorageServiceImpl destinationStorageService;

    @BeforeEach
    void setUp() {
        destinationStorageService = new LocalFileSystemDestinationStorageServiceImpl(destinationStorageProxy);
    }

    @Test
    void testPutObjectSuccess() throws SaveFailedException {
        String bucketName = "testBucket";
        String fileName = "testFile.txt";
        byte[] data = "testData".getBytes();

        doNothing().when(destinationStorageProxy).putObject(bucketName, fileName, data);

        assertDoesNotThrow(() -> destinationStorageService.putObject(bucketName, fileName, data));

        verify(destinationStorageProxy, times(1)).putObject(bucketName, fileName, data);
    }

    @Test
    void testPutObjectThrowsSaveFailedException() throws SaveFailedException {
        String bucketName = "testBucket";
        String fileName = "testFile.txt";
        byte[] data = "testData".getBytes();

        doThrow(new SaveFailedException("Save failed")).when(destinationStorageProxy).putObject(bucketName, fileName, data);

        SaveFailedException exception = assertThrows(SaveFailedException.class, () -> {
            destinationStorageService.putObject(bucketName, fileName, data);
        });

        assertEquals("Save failed", exception.getMessage());
        verify(destinationStorageProxy, times(1)).putObject(bucketName, fileName, data);
    }
}
