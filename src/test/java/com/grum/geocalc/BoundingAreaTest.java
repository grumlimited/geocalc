/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grum.geocalc;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * BoundingAreaTest
 * @author blackleg
 */
public class BoundingAreaTest { 
    
    public BoundingAreaTest() {
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
     * Test of getLowerLatitude method, of class BoundingArea.
     */
    @Test
    public void testGetLowerLatitude() {
        double lowerLatitude = 50.0;
        double higherLatitude = 70.0;
        
        Point northEast = Point.build(higherLatitude, 145);
        Point southWest = Point.build(lowerLatitude, 110);
        
        BoundingArea boundingArea = new BoundingArea(northEast, southWest);
        assertNotNull(boundingArea);
        
        assertEquals(lowerLatitude, boundingArea.getLowerLatitude(), 0.0);
        
    }

    /**
     * Test of getHigherLatitude method, of class BoundingArea.
     */
    @Test
    public void testGetHigherLatitude() {
        System.out.println("getHigherLatitude");
        //BoundingArea instance = null;
        //double expResult = 0.0;
        //double result = instance.getHigherLatitude();
        //assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getLowerLongitude method, of class BoundingArea.
     */
    @Test
    public void testGetLowerLongitude() {
        System.out.println("getLowerLongitude");
        //BoundingArea instance = null;
        //double expResult = 0.0;
        //double result = instance.getLowerLongitude();
        //assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getHigherLongitude method, of class BoundingArea.
     */
    @Test
    public void testGetHigherLongitude() {
        System.out.println("getHigherLongitude");
        //BoundingArea instance = null;
        //double expResult = 0.0;
        //double result = instance.getHigherLongitude();
        //assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
}
