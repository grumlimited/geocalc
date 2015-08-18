/*
 * BSD 3-Clause License
 *
 * Copyright (c) 2015, Grumlimited Ltd (Romain Gallet)
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 *
 *  Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 *  Neither the name of Geocalc nor the names of its
 *   contributors may be used to endorse or promote products derived from
 *   this software without specific prior written permission.
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

import static java.lang.Math.abs;

/**
 * Represents coordinates given in
 * Degrees Minutes decimal-seconds (D M s) format
 *
 * @author rgallet
 */
public class DMSCoordinate extends Coordinate {

    private double wholeDegrees, minutes, seconds, hundredth;

    public DMSCoordinate(double wholeDegrees, double minutes, double seconds) {
     this(wholeDegrees, minutes, seconds, 0);
    }

    public DMSCoordinate(double wholeDegrees, double minutes, double seconds, double hundredth) {
        this.wholeDegrees = wholeDegrees;
        this.minutes = minutes;
        this.seconds = seconds;
        this.hundredth = hundredth;
        this.decimalDegrees = abs(this.wholeDegrees) + minutes / 60 + seconds / 3600 + hundredth / 360000;

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

    public double getHundredth() {
        return hundredth;
    }
}
