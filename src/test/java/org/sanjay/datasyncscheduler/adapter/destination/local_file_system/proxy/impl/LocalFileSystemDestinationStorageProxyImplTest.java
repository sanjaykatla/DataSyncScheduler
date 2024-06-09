package org.sanjay.datasyncscheduler.adapter.destination.local_file_system.proxy.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mockito;
import org.sanjay.datasyncscheduler.adapter.destination.exception.SaveFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LocalFileSystemDestinationStorageProxyImplTest {

    private LocalFileSystemDestinationStorageProxyImpl localFileSystemDestinationStorageProxy;
    private static final Logger logger = LoggerFactory.getLogger(LocalFileSystemDestinationStorageProxyImpl.class);

    @BeforeEach
    public void setUp() {
        localFileSystemDestinationStorageProxy = new LocalFileSystemDestinationStorageProxyImpl();
    }

    @Test
    public void testPutObject_Success(@TempDir Path tempDir) throws Exception {
        String fileName = tempDir.resolve("testfile.txt").toString();
        String bucketName = "testBucket";
        byte[] data = "Hello, World!".getBytes();

        localFileSystemDestinationStorageProxy.putObject(bucketName, fileName, data);

        File file = new File(fileName);
        assertTrue(file.exists(), "File should exist");
        String fileContent = Files.readString(file.toPath());
        assertEquals("Hello, World!", fileContent, "File content should match");
    }

    @Test
    public void testPutObject_SaveFailedException() {
        String fileName = "/invalid/path/testfile.txt";
        String bucketName = "testBucket";
        byte[] data = "Hello, World!".getBytes();

        Logger mockLogger = mock(Logger.class);
        Mockito.doNothing().when(mockLogger).error(anyString());
        Mockito.doNothing().when(mockLogger).debug(anyString());

        try {
            localFileSystemDestinationStorageProxy.putObject(bucketName, fileName, data);
            fail("Expected SaveFailedException to be thrown");
        } catch (SaveFailedException e) {
            assertNotNull(e.getMessage(), "Exception message should not be null");
            assertTrue(e.getMessage().contains("No such file or directory") || e.getMessage().contains("Invalid argument"), "Exception message should indicate the reason");
        }
    }
}
