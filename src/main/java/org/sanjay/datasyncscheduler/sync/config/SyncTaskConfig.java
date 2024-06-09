package org.sanjay.datasyncscheduler.sync.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.sanjay.datasyncscheduler.adapter.destination.enums.DestinationType;
import org.sanjay.datasyncscheduler.adapter.source.enums.SourceType;

@Getter
@Setter
@RequiredArgsConstructor
public class SyncTaskConfig {

    private int id;
    private SourceType sourceType;
    private DestinationType destinationType;
    private String bucketName;

    public SyncTaskConfig(int id, SourceType sourceType, DestinationType destinationType, String bucketName) {
        this.id = id;
        this.sourceType = sourceType;
        this.destinationType = destinationType;
        this.bucketName = bucketName;
    }
}
