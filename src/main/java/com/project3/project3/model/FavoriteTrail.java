package com.project3.project3.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "favorite_trails")
public class FavoriteTrail {

    @Id
    private String id;
    private String userId;
    private String trailId;
    private LocalDateTime favoriteTrailTimestamp;

    public static FavoriteTrail favoriteTrailFactory(String userId, String trailId, LocalDateTime favoriteTrailTimestamp) {
        FavoriteTrail favoriteTrail = new FavoriteTrail();
        favoriteTrail.setUserId(userId);
        favoriteTrail.setTrailId(trailId);
        favoriteTrail.setFavoriteTrailTimestamp(favoriteTrailTimestamp);
        return favoriteTrail;
    }
}

