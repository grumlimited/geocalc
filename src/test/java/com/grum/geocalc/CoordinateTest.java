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

import lombok.val;
import lombok.var;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

/**
 * @see 'http://www.csgnetwork.com/gpscoordconv.html for online converter'
 */
@RunWith(JUnit4.class)
public class CoordinateTest {

    @Test
    public void testDegreeCoordinate() {
        var degreeCoordinate = Coordinate.fromDegrees(90);
        var coordinate = degreeCoordinate.toDegreeCoordinate();
        assertEquals(degreeCoordinate.degrees(), coordinate.degrees(), 1E-4);

        val dmsCoordinate = Coordinate.fromDMS(44, 37, 14);
        degreeCoordinate = Coordinate.fromDegrees(44.620555555555555);
        assertEquals(degreeCoordinate.degrees(), dmsCoordinate.degrees(), 1E-4);
    }

    @Test
    public void testRadianCoordinate() {
        var radianCoordinate = Coordinate.fromRadians(Math.PI / 2);
        val coordinate = radianCoordinate.toDegreeCoordinate();
        assertEquals(radianCoordinate.degrees(), coordinate.degrees(), 1E-4);

        radianCoordinate = Coordinate.fromRadians(Math.PI * .57);
        val degreeCoordinate = Coordinate.fromDegrees(180 * .57);
        assertEquals(degreeCoordinate.degrees(), radianCoordinate.degrees(), 1E-4);

        val convertedBackDegreeCoordinate = radianCoordinate.toDegreeCoordinate();
        assertEquals(degreeCoordinate.degrees(), convertedBackDegreeCoordinate.degrees(), 1E-4);

        val dmsCoordinate = Coordinate.fromDMS(44, 37, 14);
        radianCoordinate = Coordinate.fromRadians(Math.toRadians(44.620555555555555));
        assertEquals(radianCoordinate.degrees(), dmsCoordinate.degrees(), 1E-4);
    }

    @Test
    public void testDMSCoordinate() {
        var dmsCoordinate = Coordinate.fromDMS(89, 59, 60.45);
        val coordinate = dmsCoordinate.toDegreeCoordinate();
        assertEquals(dmsCoordinate.degrees(), coordinate.degrees(), 1E-5);

        dmsCoordinate = Coordinate.fromDMS(175, 8, 55.45);
        val degreeCoordinate = Coordinate.fromDegrees(175.14873);
        assertEquals(degreeCoordinate.degrees(), dmsCoordinate.degrees(), 1E-5);

        val convertedBackDegreeCoordinate = dmsCoordinate.toDegreeCoordinate();
        assertEquals(degreeCoordinate.degrees(), convertedBackDegreeCoordinate.degrees(), 1E-5);

        val radianCoordinate = Coordinate.fromRadians(Math.PI * 3 / 2);
        dmsCoordinate = Coordinate.fromDMS(270, 0, 0);
        assertEquals(radianCoordinate.toDMSCoordinate().degrees(), dmsCoordinate.degrees(), 1E-5);
    }

    @Test
    public void testDMSCoordinateNegativeValue() {
        var dmsCoordinate = Coordinate.fromDMS(-46, 32, 44.16);
        assertEquals(-46.5456, dmsCoordinate.degrees(), 0);

        val degreeCoordinate = Coordinate.fromDegrees(-46.5456);
        dmsCoordinate = degreeCoordinate.toDMSCoordinate();
        assertEquals(-46, dmsCoordinate.wholeDegrees, 0);
        assertEquals(32, dmsCoordinate.minutes, 0);
        assertEquals(44.16, dmsCoordinate.seconds, 0);
    }

    @Test
    public void testGPSCoordinate() {
        var gpsCoordinate = Coordinate.fromGPS(89, 60);
        val coordinate = gpsCoordinate.toDegreeCoordinate();
        assertEquals(gpsCoordinate.degrees(), coordinate.degrees(), 1E-4);

        gpsCoordinate = Coordinate.fromGPS(175, 8.921999999999457);
        val degreeCoordinate = Coordinate.fromDegrees(175.1487);
        assertEquals(degreeCoordinate.degrees(), gpsCoordinate.degrees(), 1E-4);

        val convertedBackDegreeCoordinate = gpsCoordinate.toDegreeCoordinate();
        assertEquals(degreeCoordinate.degrees(), convertedBackDegreeCoordinate.degrees(), 1E-4);

        val radianCoordinate = Coordinate.fromRadians(Math.PI * 3 / 2);
        gpsCoordinate = Coordinate.fromGPS(270, 0);
        assertEquals(radianCoordinate.toDMSCoordinate().degrees(), gpsCoordinate.degrees(), 1E-4);
    }

    @Test
    public void testContains() {
        var northEast = Point.at(Coordinate.fromDegrees(70), Coordinate.fromDegrees(145));
        var southWest = Point.at(Coordinate.fromDegrees(50), Coordinate.fromDegrees(110));
        var boundingArea = BoundingArea.at(northEast, southWest);

        val point1 = Point.at(Coordinate.fromDegrees(60), Coordinate.fromDegrees(120));
        assertTrue(boundingArea.contains(point1));

        val point2 = Point.at(Coordinate.fromDegrees(45), Coordinate.fromDegrees(120));
        assertFalse(boundingArea.contains(point2));

        val point3 = Point.at(Coordinate.fromDegrees(85), Coordinate.fromDegrees(120));
        assertFalse(boundingArea.contains(point3));

        val point4 = Point.at(Coordinate.fromDegrees(60), Coordinate.fromDegrees(100));
        assertFalse(boundingArea.contains(point4));

        val point5 = Point.at(Coordinate.fromDegrees(60), Coordinate.fromDegrees(150));
        assertFalse(boundingArea.contains(point5));

        val point6 = Point.at(Coordinate.fromDegrees(80), Coordinate.fromDegrees(150));
        assertFalse(boundingArea.contains(point6));

        val point7 = Point.at(Coordinate.fromDegrees(35), Coordinate.fromDegrees(100));
        assertFalse(boundingArea.contains(point7));

        northEast = Point.at(Coordinate.fromDegrees(10), Coordinate.fromDegrees(45));
        southWest = Point.at(Coordinate.fromDegrees(-30), Coordinate.fromDegrees(-35));
        boundingArea = BoundingArea.at(northEast, southWest);

        val point8 = Point.at(Coordinate.fromDegrees(0), Coordinate.fromDegrees(0));
        assertTrue(boundingArea.contains(point8));

        val point9 = Point.at(Coordinate.fromDegrees(-5), Coordinate.fromDegrees(-30));
        assertTrue(boundingArea.contains(point9));

        val point10 = Point.at(Coordinate.fromDegrees(5), Coordinate.fromDegrees(30));
        assertTrue(boundingArea.contains(point10));

        val point11 = Point.at(Coordinate.fromDegrees(-35), Coordinate.fromDegrees(30));
        assertFalse(boundingArea.contains(point11));

        northEast = Point.at(Coordinate.fromDegrees(10), Coordinate.fromDegrees(-165));
        southWest = Point.at(Coordinate.fromDegrees(-30), Coordinate.fromDegrees(170));
        boundingArea = BoundingArea.at(northEast, southWest);

        val point12 = Point.at(Coordinate.fromDegrees(0), Coordinate.fromDegrees(180));
        assertTrue(boundingArea.contains(point12));

        val point13 = Point.at(Coordinate.fromDegrees(0), Coordinate.fromDegrees(-179));
        assertTrue(boundingArea.contains(point13));
    }

    @Test
    public void testConvertingToSelf() {
        val gpsCoordinate = Coordinate.fromGPS(89, 60);
        assertEquals(gpsCoordinate.toGPSCoordinate().degrees(), gpsCoordinate.degrees(), 10E-5);

        val degreeCoordinate = Coordinate.fromDegrees(-46.5456);
        assertEquals(degreeCoordinate.toDegreeCoordinate().degrees(), degreeCoordinate.degrees(), 10E-5);

        val dmsCoordinate = Coordinate.fromDMS(89, 59, 60.45);
        assertEquals(dmsCoordinate.toDMSCoordinate().degrees(), dmsCoordinate.degrees(), 10E-5);

        val radianCoordinate = Coordinate.fromRadians(Math.PI / 2);
        assertEquals(radianCoordinate.toRadianCoordinate().degrees(), radianCoordinate.degrees(), 10E-5);
    }
}
