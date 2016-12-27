/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.blackleg.java.geocalc;

import es.blackleg.java.geocalc.DegreeCoordinate;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author hector
 */
public class DegreeCoordinateTest {
    
    public DegreeCoordinateTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of newCoordinate method, of class DegreeCoordinate.
     */
    @Test
    public void testNewCoordinate() {
        double decimalDegrees = 69.69;
        DegreeCoordinate coordinate = DegreeCoordinate.newCoordinate(decimalDegrees);
        assertEquals(decimalDegrees, coordinate.getValue(), 0.0);
    }
    
}
