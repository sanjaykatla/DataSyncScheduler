package org.sanjay.datasyncscheduler.sync.service;

import org.sanjay.datasyncscheduler.adapter.source.exception.InvalidSourceKeyNameException;
import org.sanjay.datasyncscheduler.adapter.source.exception.SourceException;
import org.sanjay.datasyncscheduler.adapter.source.exception.SourceSdkClientException;
import org.sanjay.datasyncscheduler.adapter.source.exception.SourceServiceException;
import org.sanjay.datasyncscheduler.sync.config.SyncTaskConfig;

public interface TaskSplitter {

    void splitAndSubmit(SyncTaskConfig syncTaskConfig) throws SourceServiceException, InvalidSourceKeyNameException, SourceException, SourceSdkClientException;
}
