package org.sanjay.datasyncscheduler.adapter.destination.local_file_system.service.impl;

import org.sanjay.datasyncscheduler.adapter.destination.exception.SaveFailedException;
import org.sanjay.datasyncscheduler.adapter.destination.proxy.DestinationStorageProxy;
import org.sanjay.datasyncscheduler.adapter.destination.service.DestinationStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class LocalFileSystemDestinationStorageServiceImpl implements DestinationStorageService {

    private final DestinationStorageProxy destinationStorageProxy;

    public LocalFileSystemDestinationStorageServiceImpl(
            @Autowired
            @Qualifier("localFileSystemDestinationStorageProxy")
            DestinationStorageProxy destinationStorageProxy) {
        this.destinationStorageProxy = destinationStorageProxy;
    }

    public void putObject(String bucketName, String fileName, byte[] data) throws SaveFailedException {
        destinationStorageProxy.putObject(bucketName, fileName, data);
    }
}
