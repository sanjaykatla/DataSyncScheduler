package org.sanjay.datasyncscheduler.adapter.destination.local_file_system.proxy.impl;

import org.sanjay.datasyncscheduler.DataSyncSchedulerApplication;
import org.sanjay.datasyncscheduler.adapter.destination.exception.SaveFailedException;
import org.sanjay.datasyncscheduler.adapter.destination.proxy.DestinationStorageProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

@Component
@Primary
public class LocalFileSystemDestinationStorageProxyImpl implements DestinationStorageProxy {

    private static final Logger logger = LoggerFactory.getLogger(DataSyncSchedulerApplication.class);

    public void putObject(String bucketName, String fileName, byte[] data) throws SaveFailedException {

        // TODO: implement folder structure with bucket name
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(new String(data));
        } catch (IOException e) {
            logger.error(e.getMessage());
            logger.debug(Arrays.toString(e.getStackTrace()));
            throw new SaveFailedException(e.getMessage(), e.getCause());
        }
    }
}
