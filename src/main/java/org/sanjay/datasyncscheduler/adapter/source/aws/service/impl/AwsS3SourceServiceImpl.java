package org.sanjay.datasyncscheduler.adapter.source.aws.service.impl;

import org.sanjay.datasyncscheduler.adapter.source.proxy.SourceStorageProxy;
import org.sanjay.datasyncscheduler.adapter.source.service.SourceStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.sanjay.datasyncscheduler.adapter.source.exception.*;

import java.util.List;

@Component
public class AwsS3SourceServiceImpl implements SourceStorageService {

    private final SourceStorageProxy sourceStorageProxy;

    public AwsS3SourceServiceImpl(
            @Qualifier("awsSourceStorageProxy")
            @Autowired SourceStorageProxy sourceStorageProxy) {
        this.sourceStorageProxy = sourceStorageProxy;
    }

    @Override
    public List<String> listObjects(String bucketName) throws InvalidSourceKeyNameException, SourceException, SourceServiceException, SourceSdkClientException {
        return sourceStorageProxy.listObjects(bucketName);
    }

    @Override
    public byte[] getObjectAsBytes(String bucketName, String key) throws InvalidSourceKeyNameException, InvalidSourceObjectStateException, SourceServiceException, SourceSdkClientException, SourceException {
        return sourceStorageProxy.getObjectAsBytes(bucketName, key);
    }
}
