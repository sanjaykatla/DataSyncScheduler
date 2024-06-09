package org.sanjay.datasyncscheduler.sync.config;

import lombok.Getter;
import lombok.Setter;
import org.sanjay.datasyncscheduler.adapter.destination.enums.DestinationType;
import org.sanjay.datasyncscheduler.adapter.source.enums.SourceType;

@Getter
@Setter
public class TaskConfiguration {

    private int id;
    private SourceType sourceType;
    private DestinationType destinationType;
    private String bucketName;
    private int maxRetries = 3;
    private int chunkSize = 1024 * 1024;  // 1 MB

    public TaskConfiguration(int id, SourceType sourceType, DestinationType destinationType, String bucketName, int maxRetries) {
        this.id = id;
        this.sourceType = sourceType;
        this.destinationType = destinationType;
        this.bucketName = bucketName;
        this.maxRetries = maxRetries;
    }
}
