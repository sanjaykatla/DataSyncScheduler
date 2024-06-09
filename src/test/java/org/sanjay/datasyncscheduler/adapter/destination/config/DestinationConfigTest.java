package org.sanjay.datasyncscheduler.adapter.destination.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.sanjay.datasyncscheduler.adapter.destination.local_file_system.proxy.impl.LocalFileSystemDestinationStorageProxyImpl;
import org.sanjay.datasyncscheduler.adapter.destination.local_file_system.service.impl.LocalFileSystemDestinationStorageServiceImpl;
import org.sanjay.datasyncscheduler.adapter.destination.proxy.DestinationStorageProxy;
import org.sanjay.datasyncscheduler.adapter.destination.service.DestinationStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DestinationConfig.class)
class DestinationConfigTest {

    @Autowired
    private DestinationStorageProxy destinationStorageProxy;

    @Qualifier("localFileSystemDestinationStorageService")
    @Autowired
    private DestinationStorageService destinationStorageService;

    @Test
    void testLocalFileSystemDestinationStorageProxyBean() {
        assertNotNull(destinationStorageProxy);
        assertInstanceOf(LocalFileSystemDestinationStorageProxyImpl.class, destinationStorageProxy);
    }

    @Test
    void testLocalFileSystemDestinationStorageServiceBean() {
        assertNotNull(destinationStorageService);
        assertInstanceOf(LocalFileSystemDestinationStorageServiceImpl.class, destinationStorageService);
    }
}
