package com.project3.project3.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "badges")
public class Badge {

    @Id
    private String badgeId;

    private String name;
    private String criteria;
    private BadgeType type;

    // Default constructor
    public Badge() {}

    // Constructor
    public Badge(String name, String criteria, BadgeType type) {
        this.name = name;
        this.criteria = criteria;
        this.type = type;
    }

    // Getters and Setters
    public String getBadgeId() {
        return badgeId;
    }

    public void setBadgeId(String badgeId) {
        this.badgeId = badgeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCriteria() {
        return criteria;
    }

    public void setCriteria(String criteria) {
        this.criteria = criteria;
    }

    public BadgeType getType() {
        return type;
    }

    public void setType(BadgeType type) {
        this.type = type;
    }
}

