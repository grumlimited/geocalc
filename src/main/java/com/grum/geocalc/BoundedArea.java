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

/**
 * Represents an area (viewed rectangular shaped projected onto Earth), defined by its top left and bottom right
 * coordinates
 *
 * @author rgallet
 */
public class BoundedArea {
    public final Point northEast, southWest;
    public final Point southEast, northWest;

    public BoundedArea(Point northEast, Point southWest) {
        this.northEast = northEast;
        this.southWest = southWest;

        southEast = Point.at(Coordinate.fromDegrees(southWest.latitude), Coordinate.fromDegrees(northEast.longitude));
        northWest = Point.at(Coordinate.fromDegrees(northEast.latitude), Coordinate.fromDegrees(southWest.longitude));
    }

    @Override
    public String toString() {
        return "BoundingArea{" + "northEast=" + northEast + ", southWest=" + southWest + '}';
    }

    /**
     * @return true if Point point is contained withing this bounding area
     * @deprecated use contains(Point point)
     */
    @Deprecated
    public boolean isContainedWithin(Point point) {
        return contains(point);
    }

    /**
     * @param point point to check
     * @return true if Point point is contained withing this bounding area
     */
    public boolean contains(Point point) {
        boolean predicate1 = point.latitude >= this.southWest.latitude && point.latitude <= this.northEast.latitude;

        if (!predicate1) {
            return false;
        }

        boolean predicate2;

        if (southWest.longitude > northEast.longitude) { //area is going across the max/min longitude boundaries (ie. sort of back of the Earth)
            //we "split" the area in 2, longitude-wise, point only needs to be in one or the other.
            boolean predicate3 = point.longitude <= northEast.longitude && point.longitude >= -180;
            boolean predicate4 = point.longitude >= southWest.longitude && point.longitude <= 180;

            predicate2 = predicate3 || predicate4;
        } else {
            predicate2 = point.longitude >= southWest.longitude && point.longitude <= northEast.longitude;
        }

        return predicate2;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        BoundedArea other = (BoundedArea) obj;
        if (this.northEast != other.northEast && (this.northEast == null || !this.northEast.equals(other.northEast))) {
            return false;
        }

        return !(this.southWest != other.southWest && (this.southWest == null || !this.southWest.equals(other.southWest)));
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + (this.northEast != null ? this.northEast.hashCode() : 0);
        hash = 13 * hash + (this.southWest != null ? this.southWest.hashCode() : 0);
        return hash;
    }
}
