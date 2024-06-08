package org.sanjay.datasyncscheduler.adapter.source.proxy;

import org.sanjay.datasyncscheduler.adapter.source.exception.*;

import java.util.List;

public interface SourceStorageProxy {

    List<String> listObjects(String bucketName) throws InvalidSourceKeyNameException, SourceException, SourceServiceException, SourceSdkClientException;

    byte[] getObjectAsBytes(String bucketName, String key) throws InvalidSourceKeyNameException, InvalidSourceObjectStateException, SourceException, SourceServiceException, SourceSdkClientException;
}
