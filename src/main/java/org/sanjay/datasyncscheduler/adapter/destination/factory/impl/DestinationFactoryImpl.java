package org.sanjay.datasyncscheduler.adapter.destination.factory.impl;

import org.sanjay.datasyncscheduler.adapter.destination.enums.DestinationType;
import org.sanjay.datasyncscheduler.adapter.destination.factory.DestinationFactory;
import org.sanjay.datasyncscheduler.adapter.destination.service.DestinationStorageService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class DestinationFactoryImpl implements DestinationFactory {

    private final DestinationStorageService localFileSystemDestinationStorageService;

    public DestinationFactoryImpl(@Qualifier("localFileSystemDestinationStorageService") DestinationStorageService localFileSystemDestinationStorageService) {
        this.localFileSystemDestinationStorageService = localFileSystemDestinationStorageService;
    }

    @Override
    public DestinationStorageService getDestinationStorageService(DestinationType destinationType) {

        switch (destinationType) {
            case DestinationType.Local_File_System:
                return localFileSystemDestinationStorageService;
            default:
                throw new IllegalArgumentException("Invalid destination type: " + destinationType);
        }
    }
}
