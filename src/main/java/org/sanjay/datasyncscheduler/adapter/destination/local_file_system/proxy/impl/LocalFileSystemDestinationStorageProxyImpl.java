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

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
@Primary
public class LocalFileSystemDestinationStorageProxyImpl implements DestinationStorageProxy {

    private static final Logger logger = LoggerFactory.getLogger(DataSyncSchedulerApplication.class);

    private String getDesktopPath() {
        return System.getProperty("user.home") + File.separator + "Desktop";
    }

    public void putObject(String bucketName, String fileName, byte[] data) throws SaveFailedException {
        Path path =  Paths.get(getDesktopPath(), bucketName, fileName);
        File file = path.toFile();
        file.getParentFile().mkdirs(); // Create the directories if they do not exist

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(new String(data));
        } catch (IOException e) {
            logger.error(e.getMessage());
            logger.debug(Arrays.toString(e.getStackTrace()));
            throw new SaveFailedException(e.getMessage(), e.getCause());
        }
    }
}
