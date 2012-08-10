package com.grum.geocalc;

/**
 * Represents coordinates given in
 * Degrees decimal-minutes (D m) format
 *
 * @author rgallet
 */
public class GPSCoordinate extends DMSCoordinate {

    public GPSCoordinate(double wholeDegrees, double minutes) {
        super(wholeDegrees, minutes, 0);
    }
}
