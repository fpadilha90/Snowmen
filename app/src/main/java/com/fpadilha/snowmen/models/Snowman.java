package com.fpadilha.snowmen.models;

/**
 * Created by felipe on 08/07/2016.
 */
public class Snowman {
    public static String TABLE_NAME = "snowmen";
    public static String ID = "name";
    public static String LONGITUDE = "longitude";
    public static String LATITUDE = "latitude";
    public static String PHOTO = "photo";
    public static String FAVORITE = "favorite";

    private double longitude;
    private double latitude;
    private String photo;
    private String name;
    private boolean favorite;

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}
