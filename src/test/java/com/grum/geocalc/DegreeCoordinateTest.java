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
public class DegreeCoordinateTest {
    
    @Test
    public void testBuild() {
        double decimalDegrees = 69.69;
        DegreeCoordinate coordinate = DegreeCoordinate.build(decimalDegrees);
        assertEquals(decimalDegrees, coordinate.getValue(), 0.0);
    }
    
}
