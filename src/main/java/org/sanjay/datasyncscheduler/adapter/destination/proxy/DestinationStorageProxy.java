package org.sanjay.datasyncscheduler.adapter.destination.proxy;

import org.sanjay.datasyncscheduler.adapter.destination.exception.SaveFailedException;

public interface DestinationStorageProxy {

    void putObject(String bucketName, String key, byte[] data) throws SaveFailedException;
}
