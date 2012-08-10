package com.grum.geocalc;

/**
 * Represents coordinates given in
 * radian-degrees (r) format
 *
 * @author rgallet
 */
public class RadianCoordinate extends Coordinate {

    double radians;

    public RadianCoordinate(double radians) {
        this.decimalDegrees = Math.toDegrees(radians);
        this.radians = radians;
    }
}
