package org.sanjay.datasyncscheduler.adapter.source.factory.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.sanjay.datasyncscheduler.adapter.source.enums.SourceType;
import org.sanjay.datasyncscheduler.adapter.source.service.SourceStorageService;
import org.springframework.beans.factory.annotation.Qualifier;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SourceFactoryImplTest {

    @Mock
    @Qualifier("awsSourceStorageService")
    private SourceStorageService awsSourceStorageService;

    @InjectMocks
    private SourceFactoryImpl sourceFactory;

    @BeforeEach
    void setUp() {
        sourceFactory = new SourceFactoryImpl(awsSourceStorageService);
    }

    @Test
    void testGetSourceStorageServiceWithAWS() {
        SourceStorageService result = sourceFactory.getSourceStorageService(SourceType.AWS_S3);
        assertNotNull(result);
        assertEquals(awsSourceStorageService, result);
    }

    @Test
    void testGetSourceStorageServiceWithInvalidType() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            sourceFactory.getSourceStorageService(SourceType.Invalid);
        });
        assertEquals("Invalid source type: Invalid", exception.getMessage());
    }
}
