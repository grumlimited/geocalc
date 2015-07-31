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
     * @see http://stackoverflow.com/questions/877524/calculating-coordinates-given-a-bearing-and-a-distance
     */
    public static Point pointRadialDistance(Point standPoint, double bearing, double distance) {

        double rlat1 = toRadians(standPoint.getLatitude());
        double rlon1 = toRadians(standPoint.getLongitude());
        double rbearing = toRadians(bearing);
        double rdistance = distance / EARTH_DIAMETER; // normalize linear distance to radian angle

        double rlat = asin(sin(rlat1) * cos(rdistance) + cos(rlat1) * sin(rdistance) * cos(rbearing));

        double epsilon = 0.000001;

        double rlon;

        if (cos(rlat) == 0.0 || abs(cos(rlat)) < epsilon) { // Endpoint a pole
            rlon = rlon1;
        } else {
            rlon = ((rlon1 - asin(sin(rbearing) * sin(rdistance) / cos(rlat)) + PI) % (2 * PI)) - PI;
        }

        return new Point(new RadianCoordinate(rlat), new RadianCoordinate(rlon));
    }

    /**
     * Returns the bearing, in decimal degrees, from standPoint to forePoint
     *
     * @param standPoint Origin point
     * @param forePoint  Destination point
     * @return bearing, in decimal degrees
     */
    public static double getBearing(Point standPoint, Point forePoint) {
        double latitude1 = toRadians(standPoint.getLatitude());
        double longitude1 = standPoint.getLongitude();

        double latitude2 = toRadians(forePoint.getLatitude());
        double longitude2 = forePoint.getLongitude();

        double longDiff = toRadians(longitude2 - longitude1);

        //invertedBearing because it represents the angle, in radians, of standPoint from forePoint's point of view
        //we want the opposite
        double invertedBearing = ((atan2(sin(longDiff) * cos(latitude2),
                cos(latitude1) * sin(latitude2) - sin(latitude1) * cos(latitude2) * cos(longDiff))));

        double rbearing = (-invertedBearing + 2 * PI) % (2 * PI);

        return toDegrees(rbearing);
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
