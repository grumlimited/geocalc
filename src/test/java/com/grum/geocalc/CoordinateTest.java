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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
/**
 * @see http://www.csgnetwork.com/gpscoordconv.html for online converter
 */
public class CoordinateTest {

    @Test
    public void testDegreeCoordinate() {
        DegreeCoordinate degreeCoordinate = new DegreeCoordinate(90);
        Coordinate coordinate = degreeCoordinate.getDegreeCoordinate();
        assertEquals(degreeCoordinate.decimalDegrees, coordinate.decimalDegrees, 1E-4);

        DMSCoordinate dmsCoordinate = new DMSCoordinate(44, 37, 14);
        degreeCoordinate = new DegreeCoordinate(44.620555555555555);
        assertEquals(degreeCoordinate.decimalDegrees, dmsCoordinate.decimalDegrees, 1E-4);
    }

    @Test
    public void testRadianCoordinate() {
        RadianCoordinate radianCoordinate = new RadianCoordinate(Math.PI / 2);
        Coordinate coordinate = radianCoordinate.getDegreeCoordinate();
        assertEquals(radianCoordinate.decimalDegrees, coordinate.decimalDegrees, 1E-4);

        radianCoordinate = new RadianCoordinate(Math.PI * .57);
        DegreeCoordinate degreeCoordinate = new DegreeCoordinate(180 * .57);
        assertEquals(degreeCoordinate.decimalDegrees, radianCoordinate.decimalDegrees, 1E-4);

        DegreeCoordinate convertedBackDegreeCoordinate = radianCoordinate.getDegreeCoordinate();
        assertEquals(degreeCoordinate.decimalDegrees, convertedBackDegreeCoordinate.decimalDegrees, 1E-4);

        DMSCoordinate dmsCoordinate = new DMSCoordinate(44, 37, 14);
        radianCoordinate = new RadianCoordinate(Math.toRadians(44.620555555555555));
        assertEquals(radianCoordinate.decimalDegrees, dmsCoordinate.decimalDegrees, 1E-4);
    }

    @Test
    public void testDMSCoordinate() {
        DMSCoordinate dmsCoordinate = new DMSCoordinate(89, 59, 60.45);
        Coordinate coordinate = dmsCoordinate.getDegreeCoordinate();
        assertEquals(dmsCoordinate.decimalDegrees, coordinate.decimalDegrees, 1E-5);

        dmsCoordinate = new DMSCoordinate(175, 8, 55.45);
        DegreeCoordinate degreeCoordinate = new DegreeCoordinate(175.14873);
        assertEquals(degreeCoordinate.decimalDegrees, dmsCoordinate.decimalDegrees, 1E-5);

        DegreeCoordinate convertedBackDegreeCoordinate = dmsCoordinate.getDegreeCoordinate();
        assertEquals(degreeCoordinate.decimalDegrees, convertedBackDegreeCoordinate.decimalDegrees, 1E-5);

        RadianCoordinate radianCoordinate = new RadianCoordinate(Math.PI * 3 / 2);
        dmsCoordinate = new DMSCoordinate(270, 0, 0);
        assertEquals(radianCoordinate.getDMSCoordinate().decimalDegrees, dmsCoordinate.decimalDegrees, 1E-5);
    }

    @Test
    public void testDMSCoordinateNegativeValue() {
        DMSCoordinate dmsCoordinate = new DMSCoordinate(-46, 32, 44.16);
        assertEquals(-46.5456, dmsCoordinate.decimalDegrees, 0);

        DegreeCoordinate degreeCoordinate = new DegreeCoordinate(-46.5456);
        dmsCoordinate = degreeCoordinate.getDMSCoordinate();
        assertEquals(-46, dmsCoordinate.getWholeDegrees(), 0);
        assertEquals(32, dmsCoordinate.getMinutes(), 0);
        assertEquals(44.16, dmsCoordinate.getSeconds(), 0);
    }

    @Test
    public void testGPSCoordinate() {
        GPSCoordinate gpsCoordinate = new GPSCoordinate(89, 60);
        Coordinate coordinate = gpsCoordinate.getDegreeCoordinate();
        assertEquals(gpsCoordinate.decimalDegrees, coordinate.decimalDegrees, 1E-4);

        gpsCoordinate = new GPSCoordinate(175, 8.921999999999457);
        DegreeCoordinate degreeCoordinate = new DegreeCoordinate(175.1487);
        assertEquals(degreeCoordinate.decimalDegrees, gpsCoordinate.decimalDegrees, 1E-4);

        DegreeCoordinate convertedBackDegreeCoordinate = gpsCoordinate.getDegreeCoordinate();
        assertEquals(degreeCoordinate.decimalDegrees, convertedBackDegreeCoordinate.decimalDegrees, 1E-4);

        RadianCoordinate radianCoordinate = new RadianCoordinate(Math.PI * 3 / 2);
        gpsCoordinate = new GPSCoordinate(270, 0);
        assertEquals(radianCoordinate.getDMSCoordinate().decimalDegrees, gpsCoordinate.decimalDegrees, 1E-4);
    }

    @Test
    public void testIsContainedWithin() {
        Point northEast = new Point(new DegreeCoordinate(70), new DegreeCoordinate(145));
        Point southWest = new Point(new DegreeCoordinate(50), new DegreeCoordinate(110));
        BoundingArea boundingArea = new BoundingArea(northEast, southWest);

        Point point1 = new Point(new DegreeCoordinate(60), new DegreeCoordinate(120));
        assertTrue(boundingArea.isContainedWithin(point1));

        Point point2 = new Point(new DegreeCoordinate(45), new DegreeCoordinate(120));
        assertFalse(boundingArea.isContainedWithin(point2));

        Point point3 = new Point(new DegreeCoordinate(85), new DegreeCoordinate(120));
        assertFalse(boundingArea.isContainedWithin(point3));

        Point point4 = new Point(new DegreeCoordinate(60), new DegreeCoordinate(100));
        assertFalse(boundingArea.isContainedWithin(point4));

        Point point5 = new Point(new DegreeCoordinate(60), new DegreeCoordinate(150));
        assertFalse(boundingArea.isContainedWithin(point5));

        Point point6 = new Point(new DegreeCoordinate(80), new DegreeCoordinate(150));
        assertFalse(boundingArea.isContainedWithin(point6));

        Point point7 = new Point(new DegreeCoordinate(35), new DegreeCoordinate(100));
        assertFalse(boundingArea.isContainedWithin(point7));

        northEast = new Point(new DegreeCoordinate(10), new DegreeCoordinate(45));
        southWest = new Point(new DegreeCoordinate(-30), new DegreeCoordinate(-35));
        boundingArea = new BoundingArea(northEast, southWest);

        Point point8 = new Point(new DegreeCoordinate(0), new DegreeCoordinate(0));
        assertTrue(boundingArea.isContainedWithin(point8));

        Point point9 = new Point(new DegreeCoordinate(-5), new DegreeCoordinate(-30));
        assertTrue(boundingArea.isContainedWithin(point9));

        Point point10 = new Point(new DegreeCoordinate(5), new DegreeCoordinate(30));
        assertTrue(boundingArea.isContainedWithin(point10));

        Point point11 = new Point(new DegreeCoordinate(-35), new DegreeCoordinate(30));
        assertFalse(boundingArea.isContainedWithin(point11));

        northEast = new Point(new DegreeCoordinate(10), new DegreeCoordinate(-165));
        southWest = new Point(new DegreeCoordinate(-30), new DegreeCoordinate(170));
        boundingArea = new BoundingArea(northEast, southWest);

        Point point12 = new Point(new DegreeCoordinate(0), new DegreeCoordinate(180));
        assertTrue(boundingArea.isContainedWithin(point12));

        Point point13 = new Point(new DegreeCoordinate(0), new DegreeCoordinate(-179));
        assertTrue(boundingArea.isContainedWithin(point13));
    }
}
