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

    SourceType sourceType;
    DestinationType destinationType;

    public SyncTaskConfig(SourceType sourceType, DestinationType destinationType) {
        this.sourceType = sourceType;
        this.destinationType = destinationType;
    }
}
