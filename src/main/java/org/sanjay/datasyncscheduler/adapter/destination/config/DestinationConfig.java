package org.sanjay.datasyncscheduler.adapter.destination.config;

import org.sanjay.datasyncscheduler.adapter.destination.local_file_system.proxy.impl.LocalFileSystemDestinationStorageProxyImpl;
import org.sanjay.datasyncscheduler.adapter.destination.local_file_system.service.impl.LocalFileSystemDestinationStorageServiceImpl;
import org.sanjay.datasyncscheduler.adapter.destination.proxy.DestinationStorageProxy;
import org.sanjay.datasyncscheduler.adapter.destination.service.DestinationStorageService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DestinationConfig {

    @Bean(name = "localFileSystemDestinationStorageProxy")
    public DestinationStorageProxy getLocalFileSystemDestinationStorageProxy() {
        return new LocalFileSystemDestinationStorageProxyImpl();
    }

    @Bean(name = "localFileSystemDestinationStorageService")
    public DestinationStorageService getLocalFileSystemDestinationStorageService() {
        return new LocalFileSystemDestinationStorageServiceImpl(getLocalFileSystemDestinationStorageProxy());
    }
}
