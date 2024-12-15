package com.project3.project3.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.swing.plaf.basic.BasicDesktopIconUI;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "badges")
public class Badge {

    @Id
    private String badgeId;
    private String name;
    private String criteria;
    private BadgeType type;
    private String badgeObjectKey;

    public static Badge badgeFactory(String name, String criteria, BadgeType type, String badgeObjectKey) {
        Badge badge = new Badge();
        badge.setName(name);
        badge.setCriteria(criteria);
        badge.setType(type);
        badge.setBadgeObjectKey(badgeObjectKey);
        return badge;
    }
}
