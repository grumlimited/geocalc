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

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

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
        double _wholeDegrees = (int) decimalDegrees;
        double remaining = Math.abs(decimalDegrees - _wholeDegrees);
        double _minutes = (int) (remaining * 60);
        remaining = remaining * 60 - _minutes;
        double _seconds = new BigDecimal(remaining * 60).setScale(4, RoundingMode.HALF_UP).doubleValue();

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
