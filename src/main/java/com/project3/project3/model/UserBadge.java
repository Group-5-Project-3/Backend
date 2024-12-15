package com.project3.project3.model;

import com.project3.project3.service.UserBadgeService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "user_badges")
public class UserBadge {

    @Id
    private String id;
    private String userId;
    private String badgeId;
    private LocalDateTime awardedTimestamp;

    public static UserBadge userBadgeFactory(String userId, String badgeId, LocalDateTime awardedTimestamp) {
        UserBadge userBadge = new UserBadge();
        userBadge.setUserId(userId);
        userBadge.setBadgeId(badgeId);
        userBadge.setAwardedTimestamp(awardedTimestamp);
        return userBadge;
    }
}
