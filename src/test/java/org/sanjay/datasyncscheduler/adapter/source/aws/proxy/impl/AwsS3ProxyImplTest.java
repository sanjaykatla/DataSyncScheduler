package org.sanjay.datasyncscheduler.adapter.source.aws.proxy.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.sanjay.datasyncscheduler.adapter.source.exception.*;
import org.sanjay.datasyncscheduler.model.SyncObject;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AwsS3ProxyImplTest {

    @Mock
    private S3Client s3Client;

    private AwsS3ProxyImpl awsS3Proxy;

    @BeforeEach
    public void setUp() {
        awsS3Proxy = new AwsS3ProxyImpl(s3Client);
    }

    @Test
    public void testListObjects_Success() throws Exception {
        // Mock S3 response
        List<S3Object> objects = new ArrayList<>();
        objects.add(S3Object.builder().key("object1").build());
        ListObjectsResponse listObjectsResponse = ListObjectsResponse.builder().contents(objects).build();
        when(s3Client.listObjects(any(ListObjectsRequest.class))).thenReturn(listObjectsResponse);

        // Call the method
        List<SyncObject> objectKeys = awsS3Proxy.listObjects("my-bucket");

        // Assertions
        assertEquals(1, objectKeys.size());
        assertEquals("object1", objectKeys.get(0).getKey());
        verify(s3Client, times(1)).listObjects(any(ListObjectsRequest.class));
    }

    @Test
    public void testListObjects_NoSuchBucketException() throws Exception {
        // Mock S3 exception
        when(s3Client.listObjects(any(ListObjectsRequest.class))).thenThrow(NoSuchBucketException.class);

        // Expected exception
        assertThrows(InvalidSourceKeyNameException.class, () -> awsS3Proxy.listObjects("invalid-bucket"));

        // Verify interaction with S3 client
        verify(s3Client, times(1)).listObjects(any(ListObjectsRequest.class));
    }

    @Test
    public void testListObjects_S3Exception() throws Exception {
        // Mock S3 exception
        when(s3Client.listObjects(any(ListObjectsRequest.class))).thenThrow(S3Exception.class);

        // Expected exception
        assertThrows(SourceException.class, () -> awsS3Proxy.listObjects("my-bucket"));

        // Verify interaction with S3 client
        verify(s3Client, times(1)).listObjects(any(ListObjectsRequest.class));
    }

    @Test
    public void testListObjects_AwsServiceException() throws Exception {
        // Mock AWS service exception
        when(s3Client.listObjects(any(ListObjectsRequest.class))).thenThrow(AwsServiceException.class);

        // Expected exception
        assertThrows(SourceServiceException.class, () -> awsS3Proxy.listObjects("my-bucket"));

        // Verify interaction with S3 client
        verify(s3Client, times(1)).listObjects(any(ListObjectsRequest.class));
    }

    @Test
    public void testListObjects_SdkClientException() throws Exception {
        // Mock SDK client exception
        when(s3Client.listObjects(any(ListObjectsRequest.class))).thenThrow(SdkClientException.class);

        // Expected exception
        assertThrows(SourceSdkClientException.class, () -> awsS3Proxy.listObjects("my-bucket"));

        // Verify interaction with S3 client
        verify(s3Client, times(1)).listObjects(any(ListObjectsRequest.class));
    }

    @Test
    public void testGetObjectAsBytes_Success() throws Exception {
        // Mock S3 response with byte data
        byte[] data = "This is some object data".getBytes();
        GetObjectResponse getObjectResponse = GetObjectResponse.builder().contentLength((long)data.length).build();
        ResponseBytes responseBytes = ResponseBytes.fromInputStream(getObjectResponse, new ByteArrayInputStream(data)); // Simulate ResponseBytes object

        // Mock S3Client behavior (Option 1)
        when(s3Client.getObjectAsBytes(any(GetObjectRequest.class))).thenReturn(responseBytes);

        // Call the method
        byte[] retrievedData = awsS3Proxy.getObjectAsBytes("my-bucket", "object-key");

        // Assertions
        assertArrayEquals(data, retrievedData);
        verify(s3Client, times(1)).getObjectAsBytes(any(GetObjectRequest.class));
    }

    @Test
    public void testGetObjectAsBytes_NoSuchKeyException() throws Exception {
        // Mock S3 exception
        when(s3Client.getObjectAsBytes(any(GetObjectRequest.class))).thenThrow(NoSuchKeyException.class);

        // Expected exception
        assertThrows(InvalidSourceKeyNameException.class, () -> awsS3Proxy.getObjectAsBytes("my-bucket", "invalid-key"));

        // Verify interaction with S3 client
        verify(s3Client, times(1)).getObjectAsBytes(any(GetObjectRequest.class));
    }

    @Test
    public void testGetObjectAsBytes_InvalidObjectStateException() throws Exception {
        // Mock S3 exception
        when(s3Client.getObjectAsBytes(any(GetObjectRequest.class))).thenThrow(InvalidObjectStateException.class);

        // Expected exception
        assertThrows(InvalidSourceObjectStateException.class, () -> awsS3Proxy.getObjectAsBytes("my-bucket", "object-key"));

        // Verify interaction with S3 client
        verify(s3Client, times(1)).getObjectAsBytes(any(GetObjectRequest.class));
    }

    @Test
    public void testGetObjectAsBytes_S3Exception() throws Exception {
        // Mock S3 exception
        when(s3Client.getObjectAsBytes(any(GetObjectRequest.class))).thenThrow(S3Exception.class);

        // Expected exception
        assertThrows(SourceException.class, () -> awsS3Proxy.getObjectAsBytes("my-bucket", "object-key"));

        // Verify interaction with S3 client
        verify(s3Client, times(1)).getObjectAsBytes(any(GetObjectRequest.class));
    }

    @Test
    public void testGetObjectAsBytes_AwsServiceException() throws Exception {
        // Mock AWS service exception
        when(s3Client.getObjectAsBytes(any(GetObjectRequest.class))).thenThrow(AwsServiceException.class);

        // Expected exception
        assertThrows(SourceServiceException.class, () -> awsS3Proxy.getObjectAsBytes("my-bucket", "object-key"));

        // Verify interaction with S3 client
        verify(s3Client, times(1)).getObjectAsBytes(any(GetObjectRequest.class));
    }

    @Test
    public void testGetObjectAsBytes_SdkClientException() throws Exception {
        // Mock SDK client exception
        when(s3Client.getObjectAsBytes(any(GetObjectRequest.class))).thenThrow(SdkClientException.class);

        // Expected exception
        assertThrows(SourceSdkClientException.class, () -> awsS3Proxy.getObjectAsBytes("my-bucket", "object-key"));

        // Verify interaction with S3 client
        verify(s3Client, times(1)).getObjectAsBytes(any(GetObjectRequest.class));
    }

}