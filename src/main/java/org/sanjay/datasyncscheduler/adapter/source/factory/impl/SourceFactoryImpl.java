package org.sanjay.datasyncscheduler.adapter.source.factory.impl;

import org.sanjay.datasyncscheduler.adapter.source.enums.SourceType;
import org.sanjay.datasyncscheduler.adapter.source.factory.SourceFactory;
import org.sanjay.datasyncscheduler.adapter.source.service.SourceStorageService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class SourceFactoryImpl implements SourceFactory {

    private final SourceStorageService awsSourceStorageService;

    public SourceFactoryImpl(@Qualifier("awsSourceStorageService") SourceStorageService awsSourceStorageService) {
        this.awsSourceStorageService = awsSourceStorageService;
    }

    public SourceStorageService getSourceStorageService(SourceType sourceType) {
        switch (sourceType) {
            case AWS_S3:
                return awsSourceStorageService;
            default:
                throw new IllegalArgumentException("Invalid source type: " + sourceType);
        }
    }
}
