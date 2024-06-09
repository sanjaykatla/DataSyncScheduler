package org.sanjay.datasyncscheduler.adapter.destination.factory.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.sanjay.datasyncscheduler.adapter.destination.enums.DestinationType;
import org.sanjay.datasyncscheduler.adapter.destination.factory.DestinationFactory;
import org.sanjay.datasyncscheduler.adapter.destination.service.DestinationStorageService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
public class DestinationFactoryImplTest {

    @Mock
    private DestinationStorageService localFileSystemDestinationStorageService;

    private DestinationFactory destinationFactory;

    @BeforeEach
    public void setUp() {
        destinationFactory = new DestinationFactoryImpl(localFileSystemDestinationStorageService);
    }

    @Test
    public void testGetDestinationStorageService_LocalFileSystem() {
        // Set expected behavior
        DestinationType expectedType = DestinationType.Local_File_System;

        // Call the method
        DestinationStorageService service = destinationFactory.getDestinationStorageService(expectedType);

        // Assertions
        assertEquals(localFileSystemDestinationStorageService, service);
    }

    @Test
    public void testGetDestinationStorageService_InvalidType() {
        // Set unexpected type
        DestinationType unexpectedType = DestinationType.Invalid; // Assuming another supported type

        // Expected exception
        assertThrows(IllegalArgumentException.class, () -> destinationFactory.getDestinationStorageService(unexpectedType));

        // Verify no interaction with local service (optional)
        verifyNoInteractions(localFileSystemDestinationStorageService);
    }

    @Test
    public void testGetDestinationStorageService_NullType() {
        // Test with null argument
        assertThrows(NullPointerException.class, () -> destinationFactory.getDestinationStorageService(null));
    }
}