package org.sanjay.datasyncscheduler.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class SyncObject {

    private String key;
    private Long size;
}
