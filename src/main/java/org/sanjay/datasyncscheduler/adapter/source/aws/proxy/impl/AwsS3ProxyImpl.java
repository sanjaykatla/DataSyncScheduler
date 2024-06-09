package org.sanjay.datasyncscheduler.adapter.source.aws.proxy.impl;

import org.sanjay.datasyncscheduler.adapter.source.proxy.SourceStorageProxy;
import org.sanjay.datasyncscheduler.adapter.source.exception.*;
import org.sanjay.datasyncscheduler.model.SyncObject;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.util.LinkedList;
import java.util.List;

@Component
public class AwsS3ProxyImpl implements SourceStorageProxy {

    private final S3Client s3Client;

    public AwsS3ProxyImpl(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    @Override
    public List<SyncObject> listObjects(String bucketName) throws InvalidSourceKeyNameException, SourceException, SourceServiceException, SourceSdkClientException {

        List<SyncObject> objects = new LinkedList<>();
        ListObjectsRequest listObjects = ListObjectsRequest
                .builder()
                .bucket(bucketName)
                .build();
        try {

            ListObjectsResponse res = s3Client.listObjects(listObjects);
            List<S3Object> sourceObjects = res.contents();

            for(S3Object sourceObject : sourceObjects) {
                SyncObject syncObject = new SyncObject(
                        sourceObject.key(),
                        sourceObject.size(),
                        sourceObject.lastModified()
                );
                objects.add(syncObject);
            }
        } catch (NoSuchBucketException e) {
            throw new InvalidSourceKeyNameException(e.getMessage(), e.getCause());
        } catch (S3Exception e) {
            throw new SourceException(e.getMessage(), e.getCause());
        } catch (AwsServiceException e) {
            throw new SourceServiceException(e.getMessage(), e.getCause());
        } catch (SdkClientException e) {
            throw new SourceSdkClientException(e.getMessage(), e.getCause());
        }
        return objects;
    }

    @Override
    public byte[] getObjectAsBytes(String bucketName, String key) throws InvalidSourceKeyNameException, InvalidSourceObjectStateException, SourceException, SourceServiceException, SourceSdkClientException {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        byte[] data;
        try {
            data = s3Client.getObjectAsBytes(getObjectRequest).asByteArray();
        } catch (NoSuchKeyException e) {
            throw new InvalidSourceKeyNameException(e.getMessage(), e.getCause());
        } catch (InvalidObjectStateException e) {
            throw new InvalidSourceObjectStateException(e.getMessage(), e.getCause());
        } catch (S3Exception e) {
            throw new SourceException(e.getMessage(), e.getCause());
        } catch (AwsServiceException e) {
            throw new SourceServiceException(e.getMessage(), e.getCause());
        } catch (SdkClientException e) {
            throw new SourceSdkClientException(e.getMessage(), e.getCause());
        }
        return data;
    }

    @Override
    public byte[] getObjectAsBytes(String bucketName, String key, long start, long end) throws InvalidSourceKeyNameException, InvalidSourceObjectStateException, SourceException, SourceServiceException, SourceSdkClientException {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .range("bytes=" + start + "-" + end)
                .build();

        byte[] data;
        try {
            data = s3Client.getObjectAsBytes(getObjectRequest).asByteArray();
        } catch (NoSuchKeyException e) {
            throw new InvalidSourceKeyNameException(e.getMessage(), e.getCause());
        } catch (InvalidObjectStateException e) {
            throw new InvalidSourceObjectStateException(e.getMessage(), e.getCause());
        } catch (S3Exception e) {
            throw new SourceException(e.getMessage(), e.getCause());
        } catch (AwsServiceException e) {
            throw new SourceServiceException(e.getMessage(), e.getCause());
        } catch (SdkClientException e) {
            throw new SourceSdkClientException(e.getMessage(), e.getCause());
        }
        return data;
    }
}
