package org.sanjay.datasyncscheduler.adapter.source.proxy;

import org.sanjay.datasyncscheduler.adapter.source.exception.*;
import org.sanjay.datasyncscheduler.model.SyncObject;

import java.util.List;

public interface SourceStorageProxy {

    List<SyncObject> listObjects(String bucketName) throws InvalidSourceKeyNameException, SourceException, SourceServiceException, SourceSdkClientException;

    byte[] getObjectAsBytes(String bucketName, String key) throws InvalidSourceKeyNameException, InvalidSourceObjectStateException, SourceException, SourceServiceException, SourceSdkClientException;

    byte[] getObjectAsBytes(String bucketName, String key, long start, long end) throws InvalidSourceKeyNameException, InvalidSourceObjectStateException, SourceException, SourceServiceException, SourceSdkClientException;
}
