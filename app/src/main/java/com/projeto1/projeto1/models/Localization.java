package com.projeto1.projeto1.models;

/**
 * Created by michelly on 09/07/17.
 */

public class Localization {
    private Number longitude;
    private Number latitude;

    public Localization(Number longitude, Number latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Number getLongitude() {
        return longitude;
    }

    public void setLongitude(Number longitude) {
        this.longitude = longitude;
    }

    public Number getLatitude() {
        return latitude;
    }

    public void setLatitude(Number latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "Localization{" +
                "longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Localization that = (Localization) o;

        if (!longitude.equals(that.longitude)) return false;
        return latitude.equals(that.latitude);

    }

    @Override
    public int hashCode() {
        int result = longitude.hashCode();
        result = 31 * result + latitude.hashCode();
        return result;
    }
}
