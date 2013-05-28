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

import static java.lang.Math.*;

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
        this.decimalDegrees = abs(this.wholeDegrees) + minutes / 60 + seconds / 3600;

        if(wholeDegrees < 0) {
            this.decimalDegrees = -this.decimalDegrees;
        }
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
