package org.sanjay.datasyncscheduler.adapter.source.aws.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.sanjay.datasyncscheduler.adapter.source.exception.*;
import org.sanjay.datasyncscheduler.adapter.source.proxy.SourceStorageProxy;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AwsS3SourceServiceImplTest {

    @Mock
    @Qualifier("awsS3Proxy")
    private SourceStorageProxy sourceStorageProxy;

    @InjectMocks
    private AwsS3SourceServiceImpl awsS3SourceService;

    @BeforeEach
    void setUp() {
        awsS3SourceService = new AwsS3SourceServiceImpl(sourceStorageProxy);
    }

    @Test
    void testListObjectsSuccess() throws InvalidSourceKeyNameException, SourceException, SourceServiceException, SourceSdkClientException {
        String bucketName = "testBucket";
        List<String> mockObjectList = Arrays.asList("object1", "object2");

        when(sourceStorageProxy.listObjects(bucketName)).thenReturn(mockObjectList);

        List<String> result = awsS3SourceService.listObjects(bucketName);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("object1", result.get(0));
        assertEquals("object2", result.get(1));

        verify(sourceStorageProxy, times(1)).listObjects(bucketName);
    }

    @Test
    void testListObjectsThrowsException() throws InvalidSourceKeyNameException, SourceException, SourceServiceException, SourceSdkClientException {
        String bucketName = "testBucket";

        when(sourceStorageProxy.listObjects(bucketName)).thenThrow(new SourceException("Source error"));

        assertThrows(SourceException.class, () -> {
            awsS3SourceService.listObjects(bucketName);
        });

        verify(sourceStorageProxy, times(1)).listObjects(bucketName);
    }

    @Test
    void testGetObjectAsBytesSuccess() throws InvalidSourceKeyNameException, InvalidSourceObjectStateException, SourceServiceException, SourceSdkClientException, SourceException {
        String bucketName = "testBucket";
        String key = "testKey";
        byte[] mockData = "testData".getBytes();

        when(sourceStorageProxy.getObjectAsBytes(bucketName, key)).thenReturn(mockData);

        byte[] result = awsS3SourceService.getObjectAsBytes(bucketName, key);

        assertNotNull(result);
        assertArrayEquals(mockData, result);

        verify(sourceStorageProxy, times(1)).getObjectAsBytes(bucketName, key);
    }

    @Test
    void testGetObjectAsBytesThrowsException() throws InvalidSourceKeyNameException, InvalidSourceObjectStateException, SourceServiceException, SourceSdkClientException, SourceException {
        String bucketName = "testBucket";
        String key = "testKey";

        when(sourceStorageProxy.getObjectAsBytes(bucketName, key)).thenThrow(new SourceException("Source error"));

        assertThrows(SourceException.class, () -> {
            awsS3SourceService.getObjectAsBytes(bucketName, key);
        });

        verify(sourceStorageProxy, times(1)).getObjectAsBytes(bucketName, key);
    }
}
