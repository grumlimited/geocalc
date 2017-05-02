/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grum.geocalc;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author hector
 */
public class PointTest {
    
    @Test
    public void testBuild() {
        Coordinate latitude = new DegreeCoordinate(0);
        Coordinate longitude = new DegreeCoordinate(0);
        Point expResult = new Point(latitude, longitude);
        Point result = Point.build(latitude, longitude);
        assertEquals(expResult, result);
    }  
    
    
    @Test
    public void testBuildDoubleDouble() {
        double lat = 10.0;
        double lng = -10.0;
        Coordinate latitude = new DegreeCoordinate(lat);
        Coordinate longitude = new DegreeCoordinate(lng);
        Point expResult = new Point(latitude, longitude);
        Point result = Point.build(lat, lng);
        assertEquals(expResult, result);
    }
    
}
