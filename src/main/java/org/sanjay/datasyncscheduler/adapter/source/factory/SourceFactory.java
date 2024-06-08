package org.sanjay.datasyncscheduler.adapter.source.factory;

import org.sanjay.datasyncscheduler.adapter.source.enums.SourceType;
import org.sanjay.datasyncscheduler.adapter.source.service.SourceStorageService;


public interface SourceFactory {

    SourceStorageService getSourceStorageService(SourceType sourceType);
}
