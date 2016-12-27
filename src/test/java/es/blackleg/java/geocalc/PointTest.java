/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.blackleg.java.geocalc;

import es.blackleg.java.geocalc.Coordinate;
import es.blackleg.java.geocalc.Point;
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
public class PointTest {
    
    public PointTest() {
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
     * Test of create method, of class Point.
     */
    @Test
    public void testCreate() {
        Coordinate latitude = new DegreeCoordinate(0);
        Coordinate longitude = new DegreeCoordinate(0);
        Point expResult = new Point(latitude, longitude);
        Point result = Point.create(latitude, longitude);
        assertEquals(expResult, result);
    }    
}
