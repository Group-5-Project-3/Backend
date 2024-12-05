package com.project3.project3.service;

import com.project3.project3.model.Milestones;
import com.project3.project3.repository.MilestonesRepository;
import com.project3.project3.repository.UserBadgeRepository;
import com.project3.project3.utility.NationalParksList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MilestonesServiceTest {

    @InjectMocks
    private MilestonesService milestonesService;

    @Mock
    private MilestonesRepository milestonesRepository;

    @Mock
    private UserBadgeRepository userBadgeRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateMilestones() {
        String userId = "user123";
        Milestones milestones = new Milestones(userId, 0, 0.0, 0, 0.0, 0);
        when(milestonesRepository.save(any(Milestones.class))).thenReturn(milestones);

        Milestones result = milestonesService.createMilestones(userId);

        assertNotNull(result);
        assertEquals(userId, result.getUserId());
        verify(milestonesRepository, times(1)).save(any(Milestones.class));
    }

    @Test
    void testGetMilestonesByUserId_CreateIfNotExist() {
        String userId = "user123";
        when(milestonesRepository.findByUserId(userId)).thenReturn(null);
        Milestones milestones = new Milestones(userId, 0, 0.0, 0, 0.0, 0);
        when(milestonesRepository.save(any(Milestones.class))).thenReturn(milestones);

        Milestones result = milestonesService.getMilestonesByUserId(userId);

        assertNotNull(result);
        verify(milestonesRepository, times(1)).findByUserId(userId);
        verify(milestonesRepository, times(1)).save(any(Milestones.class));
    }

    @Test
    void testUpdateMilestones() {
        String userId = "user123";
        Milestones existingMilestones = new Milestones(userId, 1, 10.0, 2, 500.0, 1);
        Milestones updatedMilestones = new Milestones(userId, 2, 20.0, 3, 1000.0, 2);
        when(milestonesRepository.findByUserId(userId)).thenReturn(existingMilestones);
        when(milestonesRepository.save(any(Milestones.class))).thenReturn(updatedMilestones);

        Milestones result = milestonesService.updateMilestones(userId, updatedMilestones);

        assertNotNull(result);
        assertEquals(2, result.getTotalHikes());
        verify(milestonesRepository, times(1)).save(any(Milestones.class));
    }

    @Test
    void testIncrementTotalHikes() {
        String userId = "user123";
        Milestones milestones = new Milestones(userId, 1, 10.0, 2, 500.0, 1);
        when(milestonesRepository.findByUserId(userId)).thenReturn(milestones);
        when(milestonesRepository.save(any(Milestones.class))).thenReturn(milestones);

        Milestones result = milestonesService.incrementTotalHikes(userId);

        assertEquals(2, result.getTotalHikes());
        verify(milestonesRepository, times(1)).save(any(Milestones.class));
    }

    @Test
    void testIncrementNationalParksVisited() {
        String userId = "user123";
        String parkName = "Yosemite";
        String badgeId = "badge123";
        Milestones milestones = new Milestones(userId, 1, 10.0, 2, 500.0, 1);

        try (MockedStatic<NationalParksList> mockedNationalParksList = mockStatic(NationalParksList.class)) {
            mockedNationalParksList.when(() -> NationalParksList.isCaliforniaNationalPark(parkName)).thenReturn(true);
            mockedNationalParksList.when(() -> NationalParksList.getBadgeIdForPark(parkName)).thenReturn(badgeId);
            when(userBadgeRepository.findByUserIdAndBadgeId(userId, badgeId)).thenReturn(Optional.empty());
            when(milestonesRepository.findByUserId(userId)).thenReturn(milestones);
            when(milestonesRepository.save(any(Milestones.class))).thenReturn(milestones);

            Milestones result = milestonesService.incrementNationalParksVisited(userId, parkName);

            assertEquals(2, result.getNationalParksVisited());
            verify(milestonesRepository, times(1)).save(any(Milestones.class));
        }
    }

    @Test
    void testDeleteMilestonesByUserId() {
        String userId = "user123";
        Milestones milestones = new Milestones(userId, 1, 10.0, 2, 500.0, 1);
        when(milestonesRepository.findByUserId(userId)).thenReturn(milestones);

        milestonesService.deleteMilestonesByUserId(userId);

        verify(milestonesRepository, times(1)).delete(milestones);
    }
}
