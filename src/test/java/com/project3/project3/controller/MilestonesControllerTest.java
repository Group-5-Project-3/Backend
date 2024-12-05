package com.project3.project3.controller;

import com.project3.project3.model.Milestones;
import com.project3.project3.service.MilestonesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class MilestonesControllerTest {

    @InjectMocks
    private MilestonesController milestonesController;

    @Mock
    private MilestonesService milestonesService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetMilestonesByUserId() {
        // Arrange
        String userId = "user123";
        Milestones milestones = new Milestones(userId, 5, 20.0, 3, 1000.0, 2);
        when(milestonesService.getMilestonesByUserId(userId)).thenReturn(milestones);

        // Act
        ResponseEntity<Milestones> response = milestonesController.getMilestonesByUserId(userId);

        // Assert
        assertEquals(200, response.getStatusCode().value());
        assertEquals(userId, response.getBody().getUserId());
        assertEquals(5, response.getBody().getTotalHikes());
        verify(milestonesService, times(1)).getMilestonesByUserId(userId);
    }

    @Test
    void testUpdateMilestones() {
        // Arrange
        String userId = "user123";
        Milestones updatedMilestones = new Milestones(userId, 10, 50.0, 5, 1500.0, 3);
        when(milestonesService.updateMilestones(userId, updatedMilestones)).thenReturn(updatedMilestones);

        // Act
        ResponseEntity<Milestones> response = milestonesController.updateMilestones(userId, updatedMilestones);

        // Assert
        assertEquals(200, response.getStatusCode().value());
        assertEquals(10, response.getBody().getTotalHikes());
        verify(milestonesService, times(1)).updateMilestones(userId, updatedMilestones);
    }

    @Test
    void testDeleteMilestonesByUserId() {
        // Arrange
        String userId = "user123";
        doNothing().when(milestonesService).deleteMilestonesByUserId(userId);

        // Act
        ResponseEntity<Void> response = milestonesController.deleteMilestonesByUserId(userId);

        // Assert
        assertEquals(204, response.getStatusCode().value());
        verify(milestonesService, times(1)).deleteMilestonesByUserId(userId);
    }
}
