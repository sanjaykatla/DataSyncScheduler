package org.sanjay.datasyncscheduler.adapter.source.aws.service.impl;

import org.sanjay.datasyncscheduler.adapter.source.proxy.SourceStorageProxy;
import org.sanjay.datasyncscheduler.adapter.source.service.SourceStorageService;
import org.sanjay.datasyncscheduler.model.SyncObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.sanjay.datasyncscheduler.adapter.source.exception.*;

import java.util.List;
import java.util.Optional;

@Component
public class AwsS3SourceServiceImpl implements SourceStorageService {

    private final SourceStorageProxy sourceStorageProxy;

    public AwsS3SourceServiceImpl(
            @Qualifier("awsS3Proxy")
            @Autowired SourceStorageProxy sourceStorageProxy) {
        this.sourceStorageProxy = sourceStorageProxy;
    }

    @Override
    public List<SyncObject> listObjects(String bucketName) throws InvalidSourceKeyNameException, SourceException, SourceServiceException, SourceSdkClientException {
        return sourceStorageProxy.listObjects(bucketName);
    }

    @Override
    public byte[] getObjectAsBytes(String bucketName, String key) throws InvalidSourceKeyNameException, InvalidSourceObjectStateException, SourceServiceException, SourceSdkClientException, SourceException {
        return sourceStorageProxy.getObjectAsBytes(bucketName, key);
    }

    @Override
    public byte[] getObjectAsBytes(String bucketName, String key, long start, long end) throws InvalidSourceKeyNameException, InvalidSourceObjectStateException, SourceServiceException, SourceSdkClientException, SourceException {
        return sourceStorageProxy.getObjectAsBytes(bucketName, key, start, end);
    }
}
