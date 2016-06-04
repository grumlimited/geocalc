/*
 * BSD 3-Clause License
 *
 * Copyright (c) 2015, Grumlimited Ltd (Romain Gallet)
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 *
 *  Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 *  Neither the name of Geocalc nor the names of its
 *   contributors may be used to endorse or promote products derived from
 *   this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.grum.geocalc;

import java.io.Serializable;
import java.util.Objects;

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
     * Build new point with latitude and longitude
     * @param latitude
     * @param longitude
     * @return 
     */
    public static Point build(Coordinate latitude, Coordinate longitude) {
        return new Point(latitude, longitude);
    }
    
    /**
     * Build new point with latitude and longitude
     * @param latitude
     * @param longitude
     * @return Point
     */
    public static Point build(double latitude, double longitude) {
        return build(DegreeCoordinate.build(latitude), DegreeCoordinate.build(longitude));
    }

    /**
     * Returns latitude in decimal degrees
     *
     * @return latitude
     */
    public double getLatitude() {
        return latitude;
    }
    
    public Coordinate getLatitudeCoordinate() {
        return DegreeCoordinate.build(latitude);
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    
    public void setLatitude(Coordinate latitude) {
        if (Objects.nonNull(latitude)) {
            setLongitude(latitude.getValue());
        }
    }

    /**
     * Returns longitude in decimal degrees
     *
     * @return longitude
     */
    public double getLongitude() {
        return longitude;
    }
    
    public Coordinate getLongitudeCoordinate() {
        return DegreeCoordinate.build(longitude);
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    
    public void setLongitude(Coordinate longitude) {
        if (Objects.nonNull(longitude)) {
            setLongitude(longitude.getValue());
        }
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
