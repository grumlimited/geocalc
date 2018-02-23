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

import org.apache.log4j.Logger;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author rgallet
 */
public class DistanceTest {

    final Logger logger = Logger.getLogger(getClass());

    @Test
    public void testSphericalLawOfCosinesDistance() {
        //Kew
        Coordinate lat = new DegreeCoordinate(51.4843774);
        Coordinate lng = new DegreeCoordinate(-0.2912044);
        Point kew = new Point(lat, lng);

        //Richmond
        lat = new DegreeCoordinate(51.4613418);
        lng = new DegreeCoordinate(-0.3035466);
        Point richmond = new Point(lat, lng);

        assertEquals(2700.3261395525724, EarthCalc.getDistance(richmond, kew), 10E-3);
    }

    @Test
    public void testDistanceBnaToLax() {
        Coordinate lat = new DegreeCoordinate(36.12);
        Coordinate lng = new DegreeCoordinate(-86.97);
        Point BNA = new Point(lat, lng);

        lat = new DegreeCoordinate(33.94);
        lng = new DegreeCoordinate(-118.40);
        Point LAX = new Point(lat, lng);

        assertEquals(2859586.477757082, EarthCalc.getDistance(LAX, BNA), 10E-3);
    }

    @Test
    public void testDistanceToBuenosAires() {
        //Kew
        Coordinate lat = new DMSCoordinate(51, 29, 3.7572);
        Coordinate lng = new DMSCoordinate(0, 17, 28.3338);

        Point kew = new Point(lat, lng);

        //Buenos Aires
        lat = new DMSCoordinate(-34, 36, 35.9994);
        lng = new DMSCoordinate(-58, 22, 11.9994);

        Point buenosAires = new Point(lat, lng);

        assertEquals(11146, (int) (EarthCalc.getDistance(buenosAires, kew) / 1000)); //km
    }

    @Test
    public void testHarvesineDistanceToBuenosAires() {
        //Kew
        Coordinate lat = new DMSCoordinate(51, 29, 3.7572);
        Coordinate lng = new DMSCoordinate(0, 17, 28.3338);

        Point kew = new Point(lat, lng);

        //Buenos Aires
        lat = new DMSCoordinate(-34, 36, 35.9994);
        lng = new DMSCoordinate(-58, 22, 11.9994);

        Point buenosAires = new Point(lat, lng);

        assertEquals(11146, (int) (EarthCalc.getHarvesineDistance(buenosAires, kew) / 1000)); //km
    }

    @Test
    public void testVincentyDistanceToBuenosAires() {
        //Kew
        Coordinate lat = new DMSCoordinate(51, 29, 3.7572);
        Coordinate lng = new DMSCoordinate(0, 17, 28.3338);

        Point kew = new Point(lat, lng);

        //Buenos Aires
        lat = new DMSCoordinate(-34, 36, 35.9994);
        lng = new DMSCoordinate(-58, 22, 11.9994);

        Point buenosAires = new Point(lat, lng);

        assertEquals(11120, (int) (EarthCalc.getVincentyDistance(buenosAires, kew) / 1000)); //km
    }

    @Test
    public void testSymmetricDistance() {
        //Kew
        Coordinate lat = new DegreeCoordinate(51.4843774);
        Coordinate lng = new DegreeCoordinate(-0.2912044);
        Point kew = new Point(lat, lng);

        //Richmond
        lat = new DegreeCoordinate(51.4613418);
        lng = new DegreeCoordinate(-0.3035466);
        Point richmond = new Point(lat, lng);

        assertEquals(EarthCalc.getDistance(richmond, kew), EarthCalc.getDistance(kew, richmond), 10E-10);
    }

    @Test
    public void testZeroDistance() {
        //Kew
        Coordinate lat = new DegreeCoordinate(51.4843774);
        Coordinate lng = new DegreeCoordinate(-0.2912044);
        Point kew = new Point(lat, lng);

        assertEquals(EarthCalc.getDistance(kew, kew), 0, 0);
    }

    @Test
    public void testBoundingAreaDistance() {
        //Kew
        Coordinate lat = new DegreeCoordinate(51.4843774);
        Coordinate lng = new DegreeCoordinate(-0.2912044);
        Point kew = new Point(lat, lng);

        BoundingArea area = EarthCalc.getBoundingArea(kew, 3000);

        double northEastDistance = EarthCalc.getDistance(kew, area.getNorthEast());
        logger.info("North East => " + northEastDistance);
        assertEquals(3000d, northEastDistance, 1E-3);

        double southWestDistance = EarthCalc.getDistance(kew, area.getSouthWest());
        logger.info("South West => " + southWestDistance);
        assertEquals(3000d, southWestDistance, 1E-3);

        Point northWest = area.getNorthWest();
        double northWestDistance = EarthCalc.getDistance(kew, northWest);
        logger.info("North West => " + northWestDistance);
        assertEquals(3000d, northWestDistance, 2);

        Point southEast = area.getSouthEast();
        double southEastDistance = EarthCalc.getDistance(kew, southEast);
        logger.info("South East => " + southEastDistance);
        assertEquals(3000d, southEastDistance, 2);

        Point middleNorth = new Point(new DegreeCoordinate(area.getNorthEast().latitude),
                new DegreeCoordinate((area.getSouthWest().longitude + area.getNorthEast().longitude) / 2));
        double middleNorthDistance = EarthCalc.getDistance(kew, middleNorth);
        logger.info("Middle North => " + middleNorthDistance);
        assertEquals(2120d, middleNorthDistance, 1);

        Point middleSouth = new Point(new DegreeCoordinate(area.getSouthWest().latitude),
                new DegreeCoordinate((area.getSouthWest().longitude + area.getNorthEast().longitude) / 2));
        double middleSouthDistance = EarthCalc.getDistance(kew, middleSouth);
        logger.info("Middle South => " + middleSouthDistance);
        assertEquals(2120d, middleSouthDistance, 2);

        Point middleWest = new Point(new DegreeCoordinate((area.getNorthEast().latitude + area.getSouthWest().latitude) / 2),
                new DegreeCoordinate(area.getNorthEast().longitude));
        double middleWestDistance = EarthCalc.getDistance(kew, middleWest);
        logger.info("Middle West => " + middleWestDistance);
        assertEquals(2120d, middleWestDistance, 3);

        Point middleEast = new Point(new DegreeCoordinate((area.getNorthEast().latitude + area.getSouthWest().latitude) / 2),
                new DegreeCoordinate(area.getSouthWest().longitude));
        double middleEastDistance = EarthCalc.getDistance(kew, middleEast);
        logger.info("Middle East => " + middleEastDistance);
        assertEquals(2120d, middleEastDistance, 1);
    }

    @Test
    public void testBoundingAreaNorthPole() {
        //North Pole
        Coordinate lat = new DegreeCoordinate(90d);
        Coordinate lng = new DegreeCoordinate(0);
        Point northPole = new Point(lat, lng);

        BoundingArea area = EarthCalc.getBoundingArea(northPole, 10000);
        logger.info("North East => " + area.getNorthEast());
        logger.info("South West => " + area.getSouthWest());

        assertEquals(89.91006798056583d, area.getNorthEast().getLatitude(), 1);
        assertEquals(90d, area.getNorthEast().getLongitude(), 1);

        assertEquals(89.91006798056583d, area.getSouthEast().getLatitude(), 1);
        assertEquals(90d, area.getSouthEast().getLongitude(), 1);
    }

    @Test
    public void testBoundingAreaNextToLondon() {
        //North Pole
        Coordinate lat = new DegreeCoordinate(51.5085452);
        Coordinate lng = new DegreeCoordinate(-0.1997387000000117);
        Point northPole = new Point(lat, lng);

        BoundingArea area = EarthCalc.getBoundingArea(northPole, 5);
        logger.info("North East => " + area.getNorthEast());
        logger.info("South West => " + area.getSouthWest());

        assertEquals(51.508576995759306d, area.getNorthEast().getLatitude(), 1);
        assertEquals(-0.19968761404347382d, area.getNorthEast().getLongitude(), 1);

        assertEquals(51.50851340421851d, area.getSouthEast().getLatitude(), 1);
        assertEquals(-0.19968761404347382d, area.getSouthEast().getLongitude(), 1);
    }

    @Test
    public void testPointRadialDistanceZero() {
        //Kew
        Coordinate lat = new DegreeCoordinate(51.4843774);
        Coordinate lng = new DegreeCoordinate(-0.2912044);
        Point kew = new Point(lat, lng);

        Point sameKew = EarthCalc.pointRadialDistance(kew, 45, 0);
        assertEquals(lat.getDecimalDegrees(), sameKew.latitude, 1E-10);
        assertEquals(lng.getDecimalDegrees(), sameKew.longitude, 1E-10);

        sameKew = EarthCalc.pointRadialDistance(kew, 90, 0);
        assertEquals(lat.getDecimalDegrees(), sameKew.latitude, 1E-10);
        assertEquals(lng.getDecimalDegrees(), sameKew.longitude, 1E-10);

        sameKew = EarthCalc.pointRadialDistance(kew, 180, 0);
        assertEquals(lat.getDecimalDegrees(), sameKew.latitude, 1E-10);
        assertEquals(lng.getDecimalDegrees(), sameKew.longitude, 1E-10);
    }

    @Test
    public void testPointRadialDistance() {
        //Kew
        Coordinate lat = new DegreeCoordinate(51.4843774);
        Coordinate lng = new DegreeCoordinate(-0.2912044);
        Point kew = new Point(lat, lng);

        //Richmond
        lat = new DegreeCoordinate(51.4613418);
        lng = new DegreeCoordinate(-0.3035466);
        Point richmond = new Point(lat, lng);

        double distance = EarthCalc.getDistance(kew, richmond);
        double bearing = EarthCalc.getBearing(kew, richmond);

        Point allegedRichmond = EarthCalc.pointRadialDistance(kew, bearing, distance);

        assertEquals(richmond.latitude, allegedRichmond.latitude, 10E-5);
        assertEquals(richmond.longitude, allegedRichmond.longitude, 10E-5);
    }

    @Test
    public void testBearing() {
        //Kew
        Coordinate lat = new DegreeCoordinate(51.4843774);
        Coordinate lng = new DegreeCoordinate(-0.2912044);
        Point kew = new Point(lat, lng);

        //Richmond, London
        lat = new DegreeCoordinate(51.4613418);
        lng = new DegreeCoordinate(-0.3035466);
        Point richmond = new Point(lat, lng);

        System.out.println(EarthCalc.getBearing(kew, richmond));
        assertEquals(EarthCalc.getBearing(kew, richmond), 198.4604614570758D, 10E-5);
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

        Coordinate lat = new DegreeCoordinate(31.194326398628462);
        Coordinate lng = new DegreeCoordinate(121.42127048962534);
        Point standpoint = new Point(lat, lng);

        lat = new DegreeCoordinate(31.194353394639606);
        lng = new DegreeCoordinate(121.4212814985147);
        Point forepoint = new Point(lat, lng);

        /**
         * http://www.movable-type.co.uk/scripts/latlong.html
         * returns a bearing of 019°13′50″
         * and not 19.213575108209017
         */
        DMSCoordinate d = new DMSCoordinate(19, 13, 50);
        assertEquals(EarthCalc.getBearing(standpoint, forepoint), new DMSCoordinate(19, 13, 50).getDegreeCoordinate().getDecimalDegrees(), 10E-5);
    }

    @Test
    public void testVincentyBearing() {
        //Kew
        Coordinate lat = new DegreeCoordinate(51.4843774);
        Coordinate lng = new DegreeCoordinate(-0.2912044);
        Point kew = new Point(lat, lng);

        //Richmond, London
        lat = new DegreeCoordinate(51.4613418);
        lng = new DegreeCoordinate(-0.3035466);
        Point richmond = new Point(lat, lng);

        //comparing to results from ttp://www.movable-type.co.uk/scripts/latlong.html
        assertEquals(EarthCalc.getVincentyBearing(kew, richmond), new DMSCoordinate(198, 30, 19.58).getDecimalDegrees(), 10E-5);
        assertEquals(EarthCalc.getVincentyFinalBearing(kew, richmond), new DMSCoordinate(198, 29, 44.82).getDecimalDegrees(), 10E-5);
    }
}
