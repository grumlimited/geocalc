package com.grum.geocalc;

import java.io.Serializable;

/**
 * Represent a point in spherical system
 *
 * @author rgallet
 */
public class Point implements Serializable {
    //decimal degrees
    double latitude, longitude;

    public Point(Coordinate latitude, Coordinate longitude) {
        this.latitude = latitude.getValue();
        this.longitude = longitude.getValue();
    }

    /**
     * Returns latitude in decimal degrees
     *
     * @return latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Returns longitude in decimal degrees
     *
     * @return
     */
    public double getLongitude() {
        return longitude;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Point other = (Point) obj;
        if (Double.doubleToLongBits(this.latitude) != Double.doubleToLongBits(other.latitude)) {
            return false;
        }
        if (Double.doubleToLongBits(this.longitude) != Double.doubleToLongBits(other.longitude)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 31 * hash + (int) (Double.doubleToLongBits(this.latitude) ^ (Double.doubleToLongBits(this.latitude) >>> 32));
        hash = 31 * hash + (int) (Double.doubleToLongBits(this.longitude) ^ (Double.doubleToLongBits(this.longitude) >>> 32));
        return hash;
    }

    @Override
    public String toString() {
        return "Point{" + "latitude=" + latitude + ", longitude=" + longitude + '}';
    }
}
