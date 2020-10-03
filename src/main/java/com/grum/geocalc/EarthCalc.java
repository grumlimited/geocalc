/*
 * BSD 3-Clause License
 *
 * Copyright (c) 2018, Grum Ltd (Romain Gallet)
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *
 * Neither the name of Geocalc nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
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

import lombok.AllArgsConstructor;
import lombok.val;
import lombok.var;

import static java.lang.Math.*;

/**
 * Earth related calculations.
 */
public class EarthCalc {

    public static final double EARTH_RADIUS = 6_356_752.314245D; // radius at the poles, meters

    public static class gcd {
        /**
         * This is the half-way point along a great circle path between the two points.
         *
         * @param standPoint standPoint
         * @param forePoint  standPoint
         * @return mid point
         * @see <a href="http://www.movable-type.co.uk/scripts/latlong.html"></a>
         */
        public static Point midPoint(Point standPoint, Point forePoint) {
            val λ1 = toRadians(standPoint.longitude);
            val λ2 = toRadians(forePoint.longitude);

            val φ1 = toRadians(standPoint.latitude);
            val φ2 = toRadians(forePoint.latitude);

            val Bx = cos(φ2) * cos(λ2 - λ1);
            val By = cos(φ2) * sin(λ2 - λ1);

            val φ3 = atan2(sin(φ1) + sin(φ2), sqrt((cos(φ1) + Bx) * (cos(φ1) + Bx) + By * By));
            val λ3 = λ1 + atan2(By, cos(φ1) + Bx);

            return Point.at(Coordinate.fromRadians(φ3), Coordinate.fromRadians(λ3));
        }

        /**
         * Returns the distance between two points at spherical law of cosines.
         *
         * @param standPoint The stand point
         * @param forePoint  The fore point
         * @return The distance, in meters
         */
        public static double distance(Point standPoint, Point forePoint) {

            val Δλ = toRadians(abs(forePoint.longitude - standPoint.longitude));
            val φ1 = toRadians(standPoint.latitude);
            val φ2 = toRadians(forePoint.latitude);

            //spherical law of cosines
            val sphereCos = (sin(φ1) * sin(φ2)) + (cos(φ1) * cos(φ2) * cos(Δλ));
            val c = acos(max(min(sphereCos, 1d), -1d));

            return EARTH_RADIUS * c;
        }

        /**
         * Returns the coordinates of a point which is "distance" away
         * from standPoint in the direction of "bearing"
         * <p>
         * Note: North is equal to 0 for bearing value
         *
         * @param standPoint Origin
         * @param bearing    Direction in degrees, clockwise from north
         * @param distance   distance in meters
         * @return forePoint coordinates
         * @see <a href="http://www.movable-type.co.uk/scripts/latlong.html"></a>
         */
        public static Point pointAt(Point standPoint, double bearing, double distance) {
        /*
         φ2 = asin( sin φ1 ⋅ cos δ + cos φ1 ⋅ sin δ ⋅ cos θ )
         λ2 = λ1 + atan2( sin θ ⋅ sin δ ⋅ cos φ1, cos δ − sin φ1 ⋅ sin φ2 )

         where
         φ is latitude,
         λ is longitude,
         θ is the bearing (clockwise from north),
         δ is the angular distance d/R;
         d being the distance travelled, R the earth’s radius
         */

            val φ1 = toRadians(standPoint.latitude);
            val λ1 = toRadians(standPoint.longitude);
            val θ = toRadians(bearing);
            val δ = distance / EARTH_RADIUS; // normalize linear distance to radian angle

            val φ2 = asin(sin(φ1) * cos(δ) + cos(φ1) * sin(δ) * cos(θ));
            val λ2 = λ1 + atan2(sin(θ) * sin(δ) * cos(φ1), cos(δ) - sin(φ1) * sin(φ2));

            val λ2_harmonised = (λ2 + 3 * PI) % (2 * PI) - PI; // normalise to −180..+180°

            return Point.at(Coordinate.fromRadians(φ2), Coordinate.fromRadians(λ2_harmonised));
        }

        /**
         * Returns the (azimuth) bearing, in decimal degrees, from standPoint to forePoint
         *
         * @param standPoint Origin point
         * @param forePoint  Destination point
         * @return (azimuth) bearing, in decimal degrees
         */
        public static double bearing(Point standPoint, Point forePoint) {
            /*
             * Formula: θ = atan2( 	sin(Δlong).cos(lat2), cos(lat1).sin(lat2) − sin(lat1).cos(lat2).cos(Δlong) )
             */

            val Δlong = toRadians(forePoint.longitude - standPoint.longitude);
            val y = sin(Δlong) * cos(toRadians(forePoint.latitude));
            val x = cos(toRadians(standPoint.latitude)) * sin(toRadians(forePoint.latitude))
                    - sin(toRadians(standPoint.latitude)) * cos(toRadians(forePoint.latitude)) * cos(Δlong);

            val bearing = (atan2(y, x) + 2 * PI) % (2 * PI);

            return toDegrees(bearing);
        }

        /**
         * Returns an area around standPoint
         *
         * @param standPoint The centre of the area
         * @param distance   Distance around standPoint, im meters
         * @return The area
         * @see <a href="http://www.movable-type.co.uk/scripts/latlong.html"></a>
         */
        public static BoundingArea around(Point standPoint, double distance) {

            //45 degrees going north-east
            val northEast = pointAt(standPoint, 45, distance);

            //225 degrees going south-west
            val southWest = pointAt(standPoint, 225, distance);

            return BoundingArea.at(northEast, southWest);
        }
    }

    public static class haversine {
        /**
         * Returns the distance between two points at Haversine formula.
         *
         * @param standPoint The stand point
         * @param forePoint  The fore point
         * @return The distance, in meters
         */
        public static double distance(Point standPoint, Point forePoint) {

            val Δλ = toRadians(abs(forePoint.longitude - standPoint.longitude));
            val φ1 = toRadians(standPoint.latitude);
            val φ2 = toRadians(forePoint.latitude);

            // haversine formula
            val Δφ = toRadians(abs(forePoint.latitude - standPoint.latitude));
            val a = sin(Δφ / 2) * sin(Δφ / 2) + cos(φ1) * cos(φ2) * sin(Δλ / 2) * sin(Δλ / 2);
            val c = 2 * atan2(sqrt(a), sqrt(1 - a)); //angular distance in radians

            return EARTH_RADIUS * c;
        }
    }

    public static class vincenty {
        /**
         * Calculate distance, (azimuth) bearing and final bearing between standPoint and forePoint.
         *
         * @param standPoint The stand point
         * @param forePoint  The fore point
         * @return Vincenty object which holds all 3 values
         */
        private static Vincenty vincenty(Point standPoint, Point forePoint) {
            val λ1 = toRadians(standPoint.longitude);
            val λ2 = toRadians(forePoint.longitude);

            val φ1 = toRadians(standPoint.latitude);
            val φ2 = toRadians(forePoint.latitude);

            val a = 6_378_137D; // radius at equator
            val b = EARTH_RADIUS; // Using b to keep close to academic formula.
            val f = 1 / 298.257223563D; // flattening of the ellipsoid

            val L = λ2 - λ1;
            val tanU1 = (1 - f) * tan(φ1);
            val cosU1 = 1 / sqrt((1 + tanU1 * tanU1));
            val sinU1 = tanU1 * cosU1;

            val tanU2 = (1 - f) * tan(φ2);
            val cosU2 = 1 / sqrt((1 + tanU2 * tanU2));
            val sinU2 = tanU2 * cosU2;

            double λ = L, λʹ, iterationLimit = 100, cosSqα, σ, cos2σM, cosσ, sinσ, sinλ, cosλ;
            do {
                sinλ = sin(λ);
                cosλ = cos(λ);
                val sinSqσ = (cosU2 * sinλ) * (cosU2 * sinλ) + (cosU1 * sinU2 - sinU1 * cosU2 * cosλ) * (cosU1 * sinU2 - sinU1 * cosU2 * cosλ);
                sinσ = sqrt(sinSqσ);
                if (sinσ == 0) return Vincenty.CO_INCIDENT_POINTS;  // co-incident points
                cosσ = sinU1 * sinU2 + cosU1 * cosU2 * cosλ;
                σ = atan2(sinσ, cosσ);
                val sinα = cosU1 * cosU2 * sinλ / sinσ;
                cosSqα = 1 - sinα * sinα;
                cos2σM = cosσ - 2 * sinU1 * sinU2 / cosSqα;

                if (Double.isNaN(cos2σM)) cos2σM = 0;  // equatorial line: cosSqα=0
                val C = f / 16 * cosSqα * (4 + f * (4 - 3 * cosSqα));
                λʹ = λ;
                λ = L + (1 - C) * f * sinα * (σ + C * sinσ * (cos2σM + C * cosσ * (-1 + 2 * cos2σM * cos2σM)));
            } while (abs(λ - λʹ) > 1e-12 && --iterationLimit > 0);

            if (iterationLimit == 0) throw new IllegalStateException("Formula failed to converge");

            val uSq = cosSqα * (a * a - b * b) / (b * b);
            val A = 1 + uSq / 16384 * (4096 + uSq * (-768 + uSq * (320 - 175 * uSq)));
            val B = uSq / 1024 * (256 + uSq * (-128 + uSq * (74 - 47 * uSq)));
            val Δσ = B * sinσ * (cos2σM + B / 4 * (cosσ * (-1 + 2 * cos2σM * cos2σM) -
                    B / 6 * cos2σM * (-3 + 4 * sinσ * sinσ) * (-3 + 4 * cos2σM * cos2σM)));

            val distance = b * A * (σ - Δσ);

            var initialBearing = atan2(cosU2 * sinλ, cosU1 * sinU2 - sinU1 * cosU2 * cosλ);
            initialBearing = (initialBearing + 2 * PI) % (2 * PI); //turning value to trigonometric direction

            var finalBearing = atan2(cosU1 * sinλ, -sinU1 * cosU2 + cosU1 * sinU2 * cosλ);
            finalBearing = (finalBearing + 2 * PI) % (2 * PI);  //turning value to trigonometric direction

            return new Vincenty(distance, toDegrees(initialBearing), toDegrees(finalBearing));
        }

        public static double distance(Point standPoint, Point forePoint) {
            return vincenty(standPoint, forePoint).distance;
        }

        /**
         * Returns (azimuth) bearing at Vincenty formula.
         *
         * @param standPoint The stand point
         * @param forePoint  The fore point
         * @return (azimuth) bearing in degrees to the North
         * @see <a href="http://www.movable-type.co.uk/scripts/latlong.html"></a>
         */
        public static double bearing(Point standPoint, Point forePoint) {
            return vincenty(standPoint, forePoint).initialBearing;
        }

        /**
         * Returns final bearing in direction of standPoint→forePoint at Vincenty formula.
         *
         * @param standPoint The stand point
         * @param forePoint  The fore point
         * @return (azimuth) bearing in degrees to the North
         * @see <a href="http://www.movable-type.co.uk/scripts/latlong.html"></a>
         */
        public static double finalBearing(Point standPoint, Point forePoint) {
            return vincenty(standPoint, forePoint).finalBearing;
        }

        @AllArgsConstructor
        private static class Vincenty {
            final static Vincenty CO_INCIDENT_POINTS = new Vincenty(0, 0, 0);

            /**
             * distance is the distance in meter
             * initialBearing is the initial bearing, or forward azimuth (in reference to North point), in degrees
             * finalBearing is the final bearing (in direction p1→p2), in degrees
             */
            final double distance, initialBearing, finalBearing;
        }
    }
}
