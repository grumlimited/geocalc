package com.grum.geocalc;

/**
 * Represents coordinates given in
 * Degrees Minutes decimal-seconds (D M s) format
 *
 * @author rgallet
 */
public class DMSCoordinate extends Coordinate {

    double wholeDegrees, minutes, seconds;

    public DMSCoordinate(double wholeDegrees, double minutes, double seconds) {
        this.wholeDegrees = wholeDegrees;
        this.minutes = minutes;
        this.seconds = seconds;
        this.decimalDegrees = wholeDegrees + minutes / 60 + seconds / 3600;
    }

    public double getMinutes() {
        return minutes;
    }

    public double getWholeDegrees() {
        return wholeDegrees;
    }

    public double getSeconds() {
        return seconds;
    }
}
