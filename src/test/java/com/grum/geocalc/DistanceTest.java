/*
 * Copyright (c) 2012 Romain Gallet
 *
 * This file is part of Geocalc.
 *
 * Geocalc is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * Geocalc is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with Geocalc. If not, see http://www.gnu.org/licenses/.
 */

package com.grum.geocalc;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import lombok.var;
import org.apache.log4j.Logger;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

@Slf4j
public class DistanceTest {

    @Test
    public void testSphericalLawOfCosinesDistance() {
        //Kew
        var lat = Coordinate.fromDegrees(51.4843774);
        var lng = Coordinate.fromDegrees(-0.2912044);
        val kew = Point.at(lat, lng);

        //Richmond
        lat = Coordinate.fromDegrees(51.4613418);
        lng = Coordinate.fromDegrees(-0.3035466);
        val richmond = Point.at(lat, lng);

        assertEquals(2694.283076925272, EarthCalc.gcdDistance(richmond, kew), 10E-3);
    }

    @Test
    public void testDistanceBnaToLax() {
        var lat = Coordinate.fromDegrees(36.12);
        var lng = Coordinate.fromDegrees(-86.97);
        val BNA = Point.at(lat, lng);

        lat = Coordinate.fromDegrees(33.94);
        lng = Coordinate.fromDegrees(-118.40);
        val LAX = Point.at(lat, lng);

        assertEquals(2853187.0080671725, EarthCalc.gcdDistance(LAX, BNA), 10E-3);
    }

    @Test
    public void testDistanceToBuenosAires() {
        //Kew
        var lat = Coordinate.fromDMS(51, 29, 3.7572);
        var lng = Coordinate.fromDMS(0, 17, 28.3338);

        val kew = Point.at(lat, lng);

        //Buenos Aires
        lat = Coordinate.fromDMS(-34, 36, 35.9994);
        lng = Coordinate.fromDMS(-58, 22, 11.9994);

        val buenosAires = Point.at(lat, lng);

        assertEquals(11121, (int) (EarthCalc.gcdDistance(buenosAires, kew) / 1000)); //km
    }

    @Test
    public void testHaversineDistanceToBuenosAires() {
        //Kew
        var lat = Coordinate.fromDMS(51, 29, 3.7572);
        var lng = Coordinate.fromDMS(0, 17, 28.3338);

        val kew = Point.at(lat, lng);

        //Buenos Aires
        lat = Coordinate.fromDMS(-34, 36, 35.9994);
        lng = Coordinate.fromDMS(-58, 22, 11.9994);

        val buenosAires = Point.at(lat, lng);

        assertEquals(11121, (int) (EarthCalc.haversineDistance(buenosAires, kew) / 1000)); //km
    }

    @Test
    public void testVincentyDistanceToBuenosAires() {
        //Kew
        var lat = Coordinate.fromDMS(51, 29, 3.7572);
        var lng = Coordinate.fromDMS(0, 17, 28.3338);

        val kew = Point.at(lat, lng);

        //Buenos Aires
        lat = Coordinate.fromDMS(-34, 36, 35.9994);
        lng = Coordinate.fromDMS(-58, 22, 11.9994);

        val buenosAires = Point.at(lat, lng);

        assertEquals(11120, (int) (EarthCalc.vincentyDistance(buenosAires, kew) / 1000)); //km
    }

    @Test
    public void testSymmetricDistance() {
        //Kew
        var lat = Coordinate.fromDegrees(51.4843774);
        var lng = Coordinate.fromDegrees(-0.2912044);
        val kew = Point.at(lat, lng);

        //Richmond
        lat = Coordinate.fromDegrees(51.4613418);
        lng = Coordinate.fromDegrees(-0.3035466);
        val richmond = Point.at(lat, lng);

        assertEquals(EarthCalc.gcdDistance(richmond, kew), EarthCalc.gcdDistance(kew, richmond), 10E-10);
    }

    @Test
    public void testZeroDistance() {
        //Kew
        val lat = Coordinate.fromDegrees(51.4843774);
        val lng = Coordinate.fromDegrees(-0.2912044);
        val kew = Point.at(lat, lng);

        assertEquals(0, EarthCalc.gcdDistance(kew, kew), 0);
        assertEquals(0, EarthCalc.vincentyDistance(kew, kew), 0);
        assertEquals(0, EarthCalc.haversineDistance(kew, kew), 0);
    }

    @Test
    public void testZeroDistanceWaldshutGermany() {
        //Kew
        val lat = Coordinate.fromDegrees(47.62285);
        val lng = Coordinate.fromDegrees(8.20897);
        val waldshut = Point.at(lat, lng);

        assertEquals(0, EarthCalc.gcdDistance(waldshut, waldshut), 0);
        assertEquals(0, EarthCalc.vincentyDistance(waldshut, waldshut), 0);
        assertEquals(0, EarthCalc.haversineDistance(waldshut, waldshut), 0);
    }

    @Test
    public void testBoundingAreaDistance() {
        //Kew
        val lat = Coordinate.fromDegrees(51.4843774);
        val lng = Coordinate.fromDegrees(-0.2912044);
        val kew = Point.at(lat, lng);

        val area = EarthCalc.around(kew, 3000);

        val northEastDistance = EarthCalc.gcdDistance(kew, area.northEast);
        assertEquals(3000d, northEastDistance, 1E-3);

        val southWestDistance = EarthCalc.gcdDistance(kew, area.southWest);
        assertEquals(3000d, southWestDistance, 1E-3);

        val northWest = area.northWest;
        val northWestDistance = EarthCalc.gcdDistance(kew, northWest);
        assertEquals(3000d, northWestDistance, 2);

        val southEast = area.southEast;
        val southEastDistance = EarthCalc.gcdDistance(kew, southEast);
        assertEquals(3000d, southEastDistance, 2);

        val middleNorth = Point.at(Coordinate.fromDegrees(area.northEast.latitude),
                Coordinate.fromDegrees((area.southWest.longitude + area.northEast.longitude) / 2));
        val middleNorthDistance = EarthCalc.gcdDistance(kew, middleNorth);
        assertEquals(2120d, middleNorthDistance, 1);

        val middleSouth = Point.at(Coordinate.fromDegrees(area.southWest.latitude),
                Coordinate.fromDegrees((area.southWest.longitude + area.northEast.longitude) / 2));
        val middleSouthDistance = EarthCalc.gcdDistance(kew, middleSouth);
        assertEquals(2120d, middleSouthDistance, 2);

        val middleWest = Point.at(Coordinate.fromDegrees((area.northEast.latitude + area.southWest.latitude) / 2),
                Coordinate.fromDegrees(area.northEast.longitude));
        val middleWestDistance = EarthCalc.gcdDistance(kew, middleWest);
        log.info("Middle West => " + middleWestDistance);
        assertEquals(2120d, middleWestDistance, 3);

        val middleEast = Point.at(Coordinate.fromDegrees((area.northEast.latitude + area.southWest.latitude) / 2),
                Coordinate.fromDegrees(area.southWest.longitude));
        val middleEastDistance = EarthCalc.gcdDistance(kew, middleEast);
        log.info("Middle East => " + middleEastDistance);
        assertEquals(2120d, middleEastDistance, 1);
    }

    @Test
    public void testBoundingAreaNorthPole() {
        //North Pole
        val lat = Coordinate.fromDegrees(90d);
        val lng = Coordinate.fromDegrees(0);
        val northPole = Point.at(lat, lng);

        val area = EarthCalc.around(northPole, 10000);
        log.info("North East => " + area.northEast);
        log.info("South West => " + area.southWest);

        assertEquals(89.91006798056583d, area.northEast.latitude, 1);
        assertEquals(90d, area.northEast.longitude, 1);

        assertEquals(89.91006798056583d, area.southEast.latitude, 1);
        assertEquals(90d, area.southEast.longitude, 1);
    }

    @Test
    public void testBoundingAreaNextToLondon() {
        //North Pole
        val lat = Coordinate.fromDegrees(51.5085452);
        val lng = Coordinate.fromDegrees(-0.1997387000000117);
        val northPole = Point.at(lat, lng);

        val area = EarthCalc.around(northPole, 5);
        log.info("North East => " + area.northEast);
        log.info("South West => " + area.southWest);

        assertEquals(51.508576995759306d, area.northEast.latitude, 1);
        assertEquals(-0.19968761404347382d, area.northEast.longitude, 1);

        assertEquals(51.50851340421851d, area.southEast.latitude, 1);
        assertEquals(-0.19968761404347382d, area.southEast.longitude, 1);
    }

    @Test
    public void testPointRadialDistanceZero() {
        //Kew
        val lat = Coordinate.fromDegrees(51.4843774);
        val lng = Coordinate.fromDegrees(-0.2912044);
        Point kew = Point.at(lat, lng);

        var sameKew = EarthCalc.pointAt(kew, 45, 0);
        assertEquals(lat.degrees(), sameKew.latitude, 1E-10);
        assertEquals(lng.degrees(), sameKew.longitude, 1E-10);

        sameKew = EarthCalc.pointAt(kew, 90, 0);
        assertEquals(lat.degrees(), sameKew.latitude, 1E-10);
        assertEquals(lng.degrees(), sameKew.longitude, 1E-10);

        sameKew = EarthCalc.pointAt(kew, 180, 0);
        assertEquals(lat.degrees(), sameKew.latitude, 1E-10);
        assertEquals(lng.degrees(), sameKew.longitude, 1E-10);
    }

    @Test
    public void testPointRadialDistance() {
        //Kew
        var lat = Coordinate.fromDegrees(51.4843774);
        var lng = Coordinate.fromDegrees(-0.2912044);
        val kew = Point.at(lat, lng);

        //Richmond
        lat = Coordinate.fromDegrees(51.4613418);
        lng = Coordinate.fromDegrees(-0.3035466);
        val richmond = Point.at(lat, lng);

        val distance = EarthCalc.gcdDistance(kew, richmond);
        val bearing = EarthCalc.bearing(kew, richmond);

        val allegedRichmond = EarthCalc.pointAt(kew, bearing, distance);

        assertEquals(richmond.latitude, allegedRichmond.latitude, 10E-5);
        assertEquals(richmond.longitude, allegedRichmond.longitude, 10E-5);
    }

    @Test
    public void testBearing() {
        //Kew
        var lat = Coordinate.fromDegrees(51.4843774);
        var lng = Coordinate.fromDegrees(-0.2912044);
        val kew = Point.at(lat, lng);

        //Richmond, London
        lat = Coordinate.fromDegrees(51.4613418);
        lng = Coordinate.fromDegrees(-0.3035466);
        val richmond = Point.at(lat, lng);

        System.out.println(EarthCalc.bearing(kew, richmond));
        assertEquals(EarthCalc.bearing(kew, richmond), 198.4604614570758D, 10E-5);
    }

    @Test
    public void testBearingGitHubIssue3() {
        /**
         *
         * https://github.com/grumlimited/geocalc/issues/3
         * standpoint is 31.194326398628462:121.42127048962534
         * forepoint is 31.194353394639606:121.4212814985147
         *
         * bearing is 340.76940494442715
         *
         * but the correct result is 19.213575108209017
         */

        var lat = Coordinate.fromDegrees(31.194326398628462);
        var lng = Coordinate.fromDegrees(121.42127048962534);
        val standpoint = Point.at(lat, lng);

        lat = Coordinate.fromDegrees(31.194353394639606);
        lng = Coordinate.fromDegrees(121.4212814985147);
        val forepoint = Point.at(lat, lng);

        /**
         * http://www.movable-type.co.uk/scripts/latlong.html
         * returns a bearing of 019°13′50″
         * and not 19.213575108209017
         */
        assertEquals(EarthCalc.bearing(standpoint, forepoint), Coordinate.fromDMS(19, 13, 50).degrees(), 10E-5);
    }

    @Test
    public void testVincentyBearing() {
        //Kew
        var lat = Coordinate.fromDegrees(51.4843774);
        var lng = Coordinate.fromDegrees(-0.2912044);
        val kew = Point.at(lat, lng);

        //Richmond, London
        lat = Coordinate.fromDegrees(51.4613418);
        lng = Coordinate.fromDegrees(-0.3035466);
        val richmond = Point.at(lat, lng);

        //comparing to results from http://www.movable-type.co.uk/scripts/latlong.html
        assertEquals(EarthCalc.vincentyBearing(kew, richmond), Coordinate.fromDMS(198, 30, 19.58).degrees(), 10E-5);
        assertEquals(EarthCalc.vincentyFinalBearing(kew, richmond), Coordinate.fromDMS(198, 29, 44.82).degrees(), 10E-5);
    }

    @Test
    public void testMidPoint() {
        //Kew
        var lat = Coordinate.fromDegrees(51.4843774);
        var lng = Coordinate.fromDegrees(-0.2912044);
        val kew = Point.at(lat, lng);

        //Richmond, London
        lat = Coordinate.fromDegrees(51.4613418);
        lng = Coordinate.fromDegrees(-0.3035466);
        val richmond = Point.at(lat, lng);

        //comparing to results from http://www.movable-type.co.uk/scripts/latlong.html
        assertEquals(EarthCalc.midPoint(richmond, kew), Point.at(Coordinate.fromDegrees(51.47285976194266), Coordinate.fromDegrees(-0.2973770580524634)));
    }
}
