/*
 * BSD 3-Clause License
 *
 * Copyright (c) 2018, Grum Ltd (Romain Gallet)
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *
 * Neither the name of Geocalc nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.grum.geocalc;
import lombok.val;
import lombok.var;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

import static java.lang.Math.*;

/**
 * Abstraction of coordinate systems (degrees, radians, dms and gps)
 */
abstract public class Coordinate implements Serializable {

    abstract double degrees();

    public static DegreeCoordinate fromDegrees(double decimalDegrees) {
        return new DegreeCoordinate(decimalDegrees);
    }

    public static DMSCoordinate fromDMS(double wholeDegrees, double minutes, double seconds) {
        return new DMSCoordinate(wholeDegrees, minutes, seconds);
    }

    public static GPSCoordinate fromGPS(double wholeDegrees, double minutes) {
        return new GPSCoordinate(wholeDegrees, minutes);
    }

    public static RadianCoordinate fromRadians(double radians) {
        return new RadianCoordinate(radians);
    }

    DMSCoordinate toDMSCoordinate() {
        val wholeDegrees = (int) degrees();
        var remaining = abs(degrees() - wholeDegrees);
        val minutes = (int) (remaining * 60);
        remaining = remaining * 60 - minutes;
        val seconds = new BigDecimal(remaining * 60).setScale(4, RoundingMode.HALF_UP).doubleValue();

        return new DMSCoordinate(wholeDegrees, minutes, seconds);
    }

    DegreeCoordinate toDegreeCoordinate() {
        return new DegreeCoordinate(degrees());
    }

    GPSCoordinate toGPSCoordinate() {
        val wholeDegrees = floor(degrees());
        val remaining = degrees() - wholeDegrees;
        val minutes = floor(remaining * 60);

        return new GPSCoordinate(wholeDegrees, minutes);
    }

    RadianCoordinate toRadianCoordinate() {
        return new RadianCoordinate(toRadians(degrees()));
    }
}
