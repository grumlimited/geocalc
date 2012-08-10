package com.grum.geocalc;

/**
 * Represents coordinates given in
 * decimal-degrees (d) format
 *
 * @author rgallet
 */
public class DegreeCoordinate extends Coordinate {

    public DegreeCoordinate(double decimalDegrees) {
        this.decimalDegrees = decimalDegrees;
    }
}
