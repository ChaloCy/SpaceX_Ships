package com.chalo.spacexships.models;

public class Ship {
    private final String id;
    private final String name;
    private final String imageUrl;
    private final String homePort;
    private final String yearBuilt;
    private final String type;

    public Ship(String id, String name, String imageUrl, String homePort, String yearBuilt, String type) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.homePort = homePort;
        this.yearBuilt = yearBuilt;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getHomePort() {
        return homePort;
    }

    public String getYearBuilt() {
        return yearBuilt;
    }

    public String getType() {
        return type;
    }
}
