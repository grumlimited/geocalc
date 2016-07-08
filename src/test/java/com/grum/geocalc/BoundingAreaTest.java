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
    
    @Test
    public void testNorthLatitude() {
        double northLatitude = 70.0;
        Point northEast = Point.build(northLatitude, 145);
        Point southWest = Point.build(50, 110);
        BoundingArea boundingArea = new BoundingArea(northEast, southWest);
        assertNotNull(boundingArea);
        assertEquals(northLatitude, boundingArea.getNorthLatitude(), 0.0);
    }
    
    @Test
    public void testSouthLatitude() {
        double shouthLatitude = 50.0;
        Point northEast = Point.build(70, 145);
        Point southWest = Point.build(shouthLatitude, 110);
        BoundingArea boundingArea = new BoundingArea(northEast, southWest);
        assertNotNull(boundingArea);
        assertEquals(shouthLatitude, boundingArea.getSouthLatitude(), 0.0);
    }
    
    
    @Test
    public void testGetWestLongitude() {
        double westLongitude = 110.0;
        Point northEast = Point.build(70, 145);
        Point southWest = Point.build(50, westLongitude);
        BoundingArea boundingArea = new BoundingArea(northEast, southWest);
        assertNotNull(boundingArea);
        assertEquals(westLongitude, boundingArea.getWestLongitude(), 0.0);
    }
    
    @Test
    public void testGetEastLatitude() {
        double eastLongitude = 145.0;
        Point northEast = Point.build(70, eastLongitude);
        Point southWest = Point.build(50.0, 110);
        BoundingArea boundingArea = new BoundingArea(northEast, southWest);
        assertNotNull(boundingArea);
        assertEquals(eastLongitude, boundingArea.getEastLongitude(), 0.0);
    }
    
}
