package com.project3.project3.service;

import com.project3.project3.model.Hike;
import com.project3.project3.repository.HikeRepository;
import com.project3.project3.utility.events.HikeEvent;
import com.project3.project3.utility.Polyline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class HikeService {

    private final HikeRepository hikeRepository;
    private final MilestonesService milestonesService;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public HikeService(HikeRepository hikeRepository, MilestonesService milestonesService, ApplicationEventPublisher applicationEventPublisher) {
        this.hikeRepository = hikeRepository;
        this.milestonesService = milestonesService;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public Hike startHike(Hike hike) {
        hike.setStartTime(LocalDateTime.now());
        String userId = hike.getUserId();
        milestonesService.incrementTotalHikes(userId);
        return hikeRepository.save(hike);
    }

    public Optional<Hike> completeHike(String hikeId, Double distance, Double elevationGain, List<List<Double>> coordinates) {
        String polyline = Polyline.encode(coordinates);
        return hikeRepository.findById(hikeId).map(existingHike -> {
            existingHike.setEndTime(LocalDateTime.now());
            existingHike.setDistance(distance);
            existingHike.setElevationGain(elevationGain);
            existingHike.setPolyline(polyline);
            String userId = existingHike.getUserId();
            milestonesService.incrementDistance(userId, distance);
            milestonesService.incrementElevationGain(userId, elevationGain);
            applicationEventPublisher.publishEvent(new HikeEvent(this, userId));
            return hikeRepository.save(existingHike);
        });
    }

    public List<Hike> getHikesByUserId(String userId) {
        return hikeRepository.findByUserId(userId);
    }

    public List<Hike> getHikesByTrailId(String trailId) {
        return hikeRepository.findByTrailId(trailId);
    }

    public Optional<Hike> getHikeById(String hikeId) {
        return hikeRepository.findById(hikeId);
    }

    public Optional<Hike> updateHike(String hikeId, Hike updatedHike) {
        return hikeRepository.findById(hikeId).map(existingHike -> {
            if (updatedHike.getStartTime() != null) {
                existingHike.setStartTime(updatedHike.getStartTime());
            }
            if (updatedHike.getEndTime() != null) {
                existingHike.setEndTime(updatedHike.getEndTime());
            }
            if (updatedHike.getDistance() != null) {
                existingHike.setDistance(updatedHike.getDistance());
            }
            if (updatedHike.getElevationGain() != null) {
                existingHike.setElevationGain(updatedHike.getElevationGain());
            }
            if (updatedHike.getPolyline() != null) {
                existingHike.setPolyline(updatedHike.getPolyline());
            }
            return hikeRepository.save(existingHike);
        });
    }

    public boolean deleteHikeById(String hikeId) {
        if (hikeRepository.existsById(hikeId)) {
            hikeRepository.deleteById(hikeId);
            return true;
        }
        return false;
    }

    public void deleteByUserId(String userId) {
        List<Hike> userHikes = hikeRepository.findByUserId(userId);
        if (!userHikes.isEmpty()) {
            hikeRepository.deleteAll(userHikes);
        }
    }
}
