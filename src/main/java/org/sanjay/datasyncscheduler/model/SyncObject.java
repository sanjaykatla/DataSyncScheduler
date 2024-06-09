package org.sanjay.datasyncscheduler.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@AllArgsConstructor
@Getter
@Setter
public class SyncObject {

    private String key;
    private Long size;
    private Instant lastModified;
}
