package org.sanjay.datasyncscheduler.adapter.source.service;


import org.sanjay.datasyncscheduler.adapter.source.exception.*;
import org.sanjay.datasyncscheduler.model.SyncObject;

import java.util.List;
import java.util.Optional;

public interface SourceStorageService {

    List<SyncObject> listObjects(String bucketName) throws InvalidSourceKeyNameException, SourceException, SourceServiceException, SourceSdkClientException;

    byte[] getObjectAsBytes(String bucketName, String key) throws InvalidSourceKeyNameException, InvalidSourceObjectStateException, SourceServiceException, SourceSdkClientException, SourceException;

    byte[] getObjectAsBytes(String bucketName, String key, long start, long end) throws InvalidSourceKeyNameException, InvalidSourceObjectStateException, SourceServiceException, SourceSdkClientException, SourceException;
}
