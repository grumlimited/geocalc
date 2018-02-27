/*
 * BSD 3-Clause License
 *
 * Copyright (c) 2017, Grumlimited Ltd (Romain Gallet)
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;

/**
 *
 * @author hector
 */
public class BoundingAreaTest {
    
    
    @Test
    public void testNorthLatitude() {
        DegreeCoordinate northLatitude = new DegreeCoordinate(70.0);
        DegreeCoordinate eastLongitude = new DegreeCoordinate(145);
        Point northEast = new Point(northLatitude, eastLongitude);
        
        DegreeCoordinate southLatitude = new DegreeCoordinate(50.0);
        DegreeCoordinate westLongitude = new DegreeCoordinate(110);
        Point southWest = new Point(southLatitude, westLongitude);
        
        BoundingArea boundingArea = new BoundingArea(northEast, southWest);
        assertNotNull(boundingArea);
        assertEquals(northLatitude.getValue(), boundingArea.getNorthLatitude(), 0.0);
    }
    
    @Test
    public void testSouthLatitude() {
        DegreeCoordinate northLatitude = new DegreeCoordinate(70.0);
        DegreeCoordinate eastLongitude = new DegreeCoordinate(145);
        Point northEast = new Point(northLatitude, eastLongitude);
        
        DegreeCoordinate southLatitude = new DegreeCoordinate(50.0);
        DegreeCoordinate westLongitude = new DegreeCoordinate(110);
        Point southWest = new Point(southLatitude, westLongitude);

        BoundingArea boundingArea = new BoundingArea(northEast, southWest);
        assertNotNull(boundingArea);
        assertEquals(southLatitude.getValue(), boundingArea.getSouthLatitude(), 0.0);
    }
    
    
    @Test
    public void testGetWestLongitude() {
        DegreeCoordinate northLatitude = new DegreeCoordinate(70.0);
        DegreeCoordinate eastLongitude = new DegreeCoordinate(145);
        Point northEast = new Point(northLatitude, eastLongitude);
        
        DegreeCoordinate southLatitude = new DegreeCoordinate(50.0);
        DegreeCoordinate westLongitude = new DegreeCoordinate(110);
        Point southWest = new Point(southLatitude, westLongitude);

        BoundingArea boundingArea = new BoundingArea(northEast, southWest);
        assertNotNull(boundingArea);
        assertEquals(westLongitude.getValue(), boundingArea.getWestLongitude(), 0.0);
    }
    
    @Test
    public void testGetEastLatitude() {
        DegreeCoordinate northLatitude = new DegreeCoordinate(70.0);
        DegreeCoordinate eastLongitude = new DegreeCoordinate(145);
        Point northEast = new Point(northLatitude, eastLongitude);
        
        DegreeCoordinate southLatitude = new DegreeCoordinate(50.0);
        DegreeCoordinate westLongitude = new DegreeCoordinate(110);
        Point southWest = new Point(southLatitude, westLongitude);

        BoundingArea boundingArea = new BoundingArea(northEast, southWest);
        assertNotNull(boundingArea);
        assertEquals(eastLongitude.getValue(), boundingArea.getEastLongitude(), 0.0);
    }
    
}
