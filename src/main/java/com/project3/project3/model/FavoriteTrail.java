package com.project3.project3.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "favorite_trails")
public class FavoriteTrail {

    @Id
    private String id;
    private String userId;
    private String trailId;
    private LocalDateTime favoriteTrailTimestamp;

    public FavoriteTrail(){
    }

    public FavoriteTrail(String id, String userId, String trailId, LocalDateTime favoriteTrailTimestamp) {
        this.id = id;
        this.userId = userId;
        this.trailId = trailId;
        this.favoriteTrailTimestamp = favoriteTrailTimestamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTrailId() {
        return trailId;
    }

    public void setTrailId(String trailId) {
        this.trailId = trailId;
    }

    public LocalDateTime getFavoriteTrailTimestamp() {
        return favoriteTrailTimestamp;
    }

    public void setFavoriteTrailTimestamp(LocalDateTime favoriteTrailTimestamp) {
        this.favoriteTrailTimestamp = favoriteTrailTimestamp;
    }

    public static FavoriteTrail favoriteTrailFactory(String userId, String trailId, LocalDateTime favoriteTrailTimestamp) {
        FavoriteTrail favoriteTrail = new FavoriteTrail();
        favoriteTrail.setUserId(userId);
        favoriteTrail.setTrailId(trailId);
        favoriteTrail.setFavoriteTrailTimestamp(favoriteTrailTimestamp);
        return favoriteTrail;
    }
}

