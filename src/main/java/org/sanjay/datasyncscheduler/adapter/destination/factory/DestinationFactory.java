package org.sanjay.datasyncscheduler.adapter.destination.factory;

import org.sanjay.datasyncscheduler.adapter.destination.enums.DestinationType;
import org.sanjay.datasyncscheduler.adapter.destination.service.DestinationStorageService;

public interface DestinationFactory {

    DestinationStorageService getDestinationStorageService(DestinationType destinationType);
}
