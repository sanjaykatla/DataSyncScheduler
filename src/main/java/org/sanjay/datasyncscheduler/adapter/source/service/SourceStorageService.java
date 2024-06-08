package org.sanjay.datasyncscheduler.adapter.source.service;


import org.sanjay.datasyncscheduler.adapter.source.exception.*;

import java.util.List;

public interface SourceStorageService {

    List<String> listObjects(String bucketName) throws InvalidSourceKeyNameException, SourceException, SourceServiceException, SourceSdkClientException;

    byte[] getObjectAsBytes(String bucketName, String key) throws InvalidSourceKeyNameException, InvalidSourceObjectStateException, SourceServiceException, SourceSdkClientException, SourceException;
}
