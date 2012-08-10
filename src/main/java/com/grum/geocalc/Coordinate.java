package com.grum.geocalc;

import java.io.Serializable;

/**
 * Represent a coordinate decimalDegrees, in degrees
 *
 * @author rgallet
 */
abstract public class Coordinate implements Serializable {

    //degrees
    double decimalDegrees;

    public double getValue() {
        return decimalDegrees;
    }

    public double getDecimalDegrees() {
        return decimalDegrees;
    }

    @Override
    public String toString() {
        return "DegreeCoordinate{" + "decimalDegrees=" + decimalDegrees + " degrees}";
    }

    DMSCoordinate getDMSCoordinate() {
        double _wholeDegrees = Math.floor(decimalDegrees);
        double remaining = decimalDegrees - _wholeDegrees;
        double _minutes = Math.floor(remaining * 60);
        remaining = remaining * 60 - _minutes;
        double _seconds = Math.floor(remaining * 3600);

        return new DMSCoordinate(_wholeDegrees, _minutes, _seconds);
    }

    DegreeCoordinate getDegreeCoordinate() {
        return new DegreeCoordinate(decimalDegrees);
    }

    GPSCoordinate getGPSCoordinate() {
        double _wholeDegrees = Math.floor(decimalDegrees);
        double remaining = decimalDegrees - _wholeDegrees;
        double _minutes = Math.floor(remaining * 60);

        return new GPSCoordinate(_wholeDegrees, _minutes);
    }

    RadianCoordinate getRadianCoordinate() {
        return new RadianCoordinate(Math.toRadians(decimalDegrees));
    }
}
