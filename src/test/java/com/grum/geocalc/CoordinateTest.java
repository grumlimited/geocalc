package com.grum.geocalc;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
/**
 * @see http://www.csgnetwork.com/gpscoordconv.html for online converter
 */
public class CoordinateTest {

    final Logger logger = Logger.getLogger(getClass());

    @Test
    public void testDegreeCoordinate() {
        DegreeCoordinate degreeCoordinate = new DegreeCoordinate(90);
        Coordinate coordinate = degreeCoordinate.getDegreeCoordinate();
        assertEquals(degreeCoordinate.getDecimalDegrees(), coordinate.getDecimalDegrees(), 1E-4);

        DMSCoordinate dmsCoordinate = new DMSCoordinate(44, 37, 14);
        degreeCoordinate = new DegreeCoordinate(44.620555555555555);
        assertEquals(degreeCoordinate.getDecimalDegrees(), dmsCoordinate.getDecimalDegrees(), 1E-4);
    }

    @Test
    public void testRadianCoordinate() {
        RadianCoordinate radianCoordinate = new RadianCoordinate(Math.PI / 2);
        Coordinate coordinate = radianCoordinate.getDegreeCoordinate();
        assertEquals(radianCoordinate.getDecimalDegrees(), coordinate.getDecimalDegrees(), 1E-4);

        radianCoordinate = new RadianCoordinate(Math.PI * .57);
        DegreeCoordinate degreeCoordinate = new DegreeCoordinate(180 * .57);
        assertEquals(degreeCoordinate.getDecimalDegrees(), radianCoordinate.getDecimalDegrees(), 1E-4);

        DegreeCoordinate convertedBackDegreeCoordinate = radianCoordinate.getDegreeCoordinate();
        assertEquals(degreeCoordinate.getDecimalDegrees(), convertedBackDegreeCoordinate.getDecimalDegrees(), 1E-4);

        DMSCoordinate dmsCoordinate = new DMSCoordinate(44, 37, 14);
        radianCoordinate = new RadianCoordinate(Math.toRadians(44.620555555555555));
        assertEquals(radianCoordinate.getDecimalDegrees(), dmsCoordinate.getDecimalDegrees(), 1E-4);
    }

    @Test
    public void testDMSCoordinate() {
        DMSCoordinate dmsCoordinate = new DMSCoordinate(89, 59, 60);
        Coordinate coordinate = dmsCoordinate.getDegreeCoordinate();
        assertEquals(dmsCoordinate.getDecimalDegrees(), coordinate.getDecimalDegrees(), 1E-4);

        dmsCoordinate = new DMSCoordinate(175, 8, 55);
        DegreeCoordinate degreeCoordinate = new DegreeCoordinate(175.1487);
        assertEquals(degreeCoordinate.getDecimalDegrees(), dmsCoordinate.getDecimalDegrees(), 1E-4);

        DegreeCoordinate convertedBackDegreeCoordinate = dmsCoordinate.getDegreeCoordinate();
        assertEquals(degreeCoordinate.getDecimalDegrees(), convertedBackDegreeCoordinate.getDecimalDegrees(), 1E-4);

        RadianCoordinate radianCoordinate = new RadianCoordinate(Math.PI * 3 / 2);
        dmsCoordinate = new DMSCoordinate(270, 0, 0);
        assertEquals(radianCoordinate.getDMSCoordinate().getDecimalDegrees(), dmsCoordinate.getDecimalDegrees(), 1E-4);
    }

    @Test
    public void testGPSCoordinate() {
        GPSCoordinate gpsCoordinate = new GPSCoordinate(89, 60);
        Coordinate coordinate = gpsCoordinate.getDegreeCoordinate();
        assertEquals(gpsCoordinate.getDecimalDegrees(), coordinate.getDecimalDegrees(), 1E-4);

        gpsCoordinate = new GPSCoordinate(175, 8.921999999999457);
        DegreeCoordinate degreeCoordinate = new DegreeCoordinate(175.1487);
        assertEquals(degreeCoordinate.getDecimalDegrees(), gpsCoordinate.getDecimalDegrees(), 1E-4);

        DegreeCoordinate convertedBackDegreeCoordinate = gpsCoordinate.getDegreeCoordinate();
        assertEquals(degreeCoordinate.getDecimalDegrees(), convertedBackDegreeCoordinate.getDecimalDegrees(), 1E-4);

        RadianCoordinate radianCoordinate = new RadianCoordinate(Math.PI * 3 / 2);
        gpsCoordinate = new GPSCoordinate(270, 0);
        assertEquals(radianCoordinate.getDMSCoordinate().getDecimalDegrees(), gpsCoordinate.getDecimalDegrees(), 1E-4);
    }
}
