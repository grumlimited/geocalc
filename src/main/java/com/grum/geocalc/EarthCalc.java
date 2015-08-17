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

import static java.lang.Math.*;

/**
 * Earth related calculations.
 *
 * @author rgallet
 */
public class EarthCalc {

    public static final double EARTH_DIAMETER = 6371.01 * 1000; //meters

    /**
     * Returns the distance between two points
     *
     * @param standPoint The stand point
     * @param forePoint  The fore point
     * @return The distance, in meters
     */
    public static double getDistance(Point standPoint, Point forePoint) {

        double diffLongitudes = toRadians(abs(forePoint.getLongitude() - standPoint.getLongitude()));
        double slat = toRadians(standPoint.getLatitude());
        double flat = toRadians(forePoint.getLatitude());

        //spherical law of cosines
        double c = acos((sin(slat) * sin(flat)) + (cos(slat) * cos(flat) * cos(diffLongitudes)));

        // haversine formula
//        double diffLatitudes = toRadians(abs(forePoint.getLatitude() - standPoint.getLatitude()));
//        double a = sin(diffLatitudes / 2) * sin(diffLatitudes / 2) + cos(slat) * cos(flat) * sin(diffLongitudes / 2) * sin(diffLongitudes / 2);
//        double c = 2 * atan2(sqrt(a), sqrt(1 - a)); //angular distance in radians

        return EARTH_DIAMETER * c;
    }

    /**
     * Returns the coordinates of a point which is "distance" away
     * from standPoint in the direction of "bearing"
     * <p>
     * Note: North is equal to 0 for bearing value
     *
     * @param standPoint Origin
     * @param bearing    Direction in degrees
     * @param distance   distance in meters
     * @return forepoint coordinates
     * @see http://www.movable-type.co.uk/scripts/latlong.html
     */
    public static Point pointRadialDistance(Point standPoint, double bearing, double distance) {
        /**
         var φ2 = Math.asin( Math.sin(φ1)*Math.cos(d/R) + Math.cos(φ1)*Math.sin(d/R)*Math.cos(brng) );
         var λ2 = λ1 + Math.atan2(Math.sin(brng)*Math.sin(d/R)*Math.cos(φ1), Math.cos(d/R)-Math.sin(φ1)*Math.sin(φ2));
         */

        double rlat1 = toRadians(standPoint.getLatitude());
        double rlon1 = toRadians(standPoint.getLongitude());
        double rbearing = toRadians(bearing);
        double rdistance = distance / EARTH_DIAMETER; // normalize linear distance to radian angle

        double lat2 = asin(sin(rlat1) * cos(rdistance) + cos(rlat1) * sin(rdistance) * cos(rbearing));
        double lon2 = rlon1 + atan2(Math.sin(rbearing) * sin(rdistance) * cos(rlat1), cos(rdistance) - sin(rlat1) * sin(lat2));

        return new Point(new RadianCoordinate(lat2), new RadianCoordinate(lon2));
    }

    /**
     * Returns the bearing, in decimal degrees, from standPoint to forePoint
     *
     * @param standPoint Origin point
     * @param forePoint  Destination point
     * @return bearing, in decimal degrees
     */
    public static double getBearing(Point standPoint, Point forePoint) {

        /**
         * Formula: θ = atan2( 	sin(Δlong).cos(lat2), cos(lat1).sin(lat2) − sin(lat1).cos(lat2).cos(Δlong) )
         */

        double y = sin(toRadians(forePoint.getLongitude() - standPoint.getLongitude())) * cos(toRadians(forePoint.getLatitude()));
        double x = cos(toRadians(standPoint.getLatitude())) * sin(toRadians(forePoint.getLatitude()))
                - sin(toRadians(standPoint.getLatitude())) * cos(toRadians(forePoint.getLatitude())) * cos(toRadians(forePoint.getLongitude() - standPoint.getLongitude()));

        double bearing = (atan2(y, x) + 2 * PI) % (2 * PI);

        return toDegrees(bearing);
    }

    /**
     * Returns an area around standPoint
     *
     * @param standPoint The centre of the area
     * @param distance   Distance around standPoint, im meters
     * @return The area
     * @see http://www.movable-type.co.uk/scripts/latlong.html
     */
    public static BoundingArea getBoundingArea(Point standPoint, double distance) {

        //45 degrees is going north-west
        Point northWest = pointRadialDistance(standPoint, 45, distance);

        //225 degrees is going south-east
        Point southEast = pointRadialDistance(standPoint, 225, distance);

        return new BoundingArea(northWest, southEast);
    }
}
