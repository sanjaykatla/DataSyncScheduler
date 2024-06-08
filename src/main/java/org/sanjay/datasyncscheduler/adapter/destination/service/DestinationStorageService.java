package org.sanjay.datasyncscheduler.adapter.destination.service;


import org.sanjay.datasyncscheduler.adapter.destination.exception.SaveFailedException;

public interface DestinationStorageService {

    void putObject(String bucketName, String key, byte[] data) throws SaveFailedException;
}
