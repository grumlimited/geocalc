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


    /**
     * Test of getLowerLatitudeValue method, of class BoundingArea.
     */
    @Test
    public void testGetLowerLatitude() {
        double southLatitude = 50.0;
        double northLatitude = 70.0;
        Point northEast = Point.build(northLatitude, 145);
        Point southWest = Point.build(southLatitude, 110);
        BoundingArea boundingArea = new BoundingArea(northEast, southWest);
        assertNotNull(boundingArea);
        assertEquals(southLatitude, boundingArea.getLowerLatitudeValue(), 0.0);
        
        northLatitude = -50.0;
        southLatitude = -70.0;
        northEast = Point.build(northLatitude, 145);
        southWest = Point.build(southLatitude, 110);
        boundingArea = new BoundingArea(northEast, southWest);
        assertNotNull(boundingArea);
        assertEquals(southLatitude, boundingArea.getLowerLatitudeValue(), 0.0);
        
        northLatitude = 20.0;
        southLatitude = -20.0;
        northEast = Point.build(northLatitude, 145);
        southWest = Point.build(southLatitude, 110);
        boundingArea = new BoundingArea(northEast, southWest);
        assertNotNull(boundingArea);
        assertEquals(southLatitude, boundingArea.getLowerLatitudeValue(), 0.0);
    }

    /**
     * Test of getHigherLatitudeValue method, of class BoundingArea.
     */
    @Test
    public void testGetHigherLatitude() {
        double northLatitude = 70.0;
        double southLatitude = 50.0;
        Point northEast = Point.build(northLatitude, 145);
        Point southWest = Point.build(southLatitude, 110);
        BoundingArea boundingArea = new BoundingArea(northEast, southWest);
        assertNotNull(boundingArea);
        assertEquals(northLatitude, boundingArea.getHigherLatitudeValue(), 0.0);
        
        northLatitude = -50.0;
        southLatitude = -70.0;
        northEast = Point.build(northLatitude, 145);
        southWest = Point.build(southLatitude, 110);
        boundingArea = new BoundingArea(northEast, southWest);
        assertNotNull(boundingArea);
        assertEquals(northLatitude, boundingArea.getHigherLatitudeValue(), 0.0);
        
        northLatitude = 20.0;
        southLatitude = -20.0;
        northEast = Point.build(northLatitude, 145);
        southWest = Point.build(southLatitude, 110);
        boundingArea = new BoundingArea(northEast, southWest);
        assertNotNull(boundingArea);
        assertEquals(northLatitude, boundingArea.getHigherLatitudeValue(), 0.0);
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

    /**
     * Test of getLowerLongitudeValue method, of class BoundingArea.
     */
    @Test
    public void testGetLowerLongitude() {
        double westLongitude = 110.0;
        double eastLongitude = 145.0;
        Point northEast = Point.build(70, eastLongitude);
        Point southWest = Point.build(50, westLongitude);
        BoundingArea boundingArea = new BoundingArea(northEast, southWest);
        assertNotNull(boundingArea);
        assertEquals(westLongitude, boundingArea.getLowerLongitudeValue(), 0.0);
        
        westLongitude = -145.0;
        eastLongitude = -110.0;
        northEast = Point.build(70, eastLongitude);
        southWest = Point.build(50, westLongitude);
        boundingArea = new BoundingArea(northEast, southWest);
        assertNotNull(boundingArea);
        assertEquals(westLongitude, boundingArea.getLowerLongitudeValue(), 0.0);
        
        westLongitude = -5.0;
        eastLongitude = 10.0;
        northEast = Point.build(70, eastLongitude);
        southWest = Point.build(50, westLongitude);
        boundingArea = new BoundingArea(northEast, southWest);
        assertNotNull(boundingArea);
        assertEquals(westLongitude, boundingArea.getLowerLongitudeValue(), 0.0);
        
        westLongitude = -5.0;
        eastLongitude = 10.0;
        northEast = Point.build(70, eastLongitude);
        southWest = Point.build(50, westLongitude);
        boundingArea = new BoundingArea(northEast, southWest);
        assertNotNull(boundingArea);
        assertEquals(westLongitude, boundingArea.getLowerLongitudeValue(), 0.0);
        
        westLongitude = 170.0;
        eastLongitude = -110.0;
        northEast = Point.build(70, eastLongitude);
        southWest = Point.build(50, westLongitude);
        boundingArea = new BoundingArea(northEast, southWest);
        assertNotNull(boundingArea);
        assertEquals(eastLongitude, boundingArea.getLowerLongitudeValue(), 0.0);
    }

    /**
     * Test of getHigherLongitudeValue method, of class BoundingArea.
     */
    @Test
    public void testGetHigherLongitude() {
        double westLongitude = 110.0;
        double eastLongitude = 145.0;
        Point northEast = Point.build(70, eastLongitude);
        Point southWest = Point.build(50, westLongitude);
        BoundingArea boundingArea = new BoundingArea(northEast, southWest);
        assertNotNull(boundingArea);
        assertEquals(eastLongitude, boundingArea.getHigherLongitudeValue(), 0.0);
        
        westLongitude = -145.0;
        eastLongitude = -110.0;
        northEast = Point.build(70, eastLongitude);
        southWest = Point.build(50, westLongitude);
        boundingArea = new BoundingArea(northEast, southWest);
        assertNotNull(boundingArea);
        assertEquals(eastLongitude, boundingArea.getHigherLongitudeValue(), 0.0);
        
        westLongitude = -5.0;
        eastLongitude = 10.0;
        northEast = Point.build(70, eastLongitude);
        southWest = Point.build(50, westLongitude);
        boundingArea = new BoundingArea(northEast, southWest);
        assertNotNull(boundingArea);
        assertEquals(eastLongitude, boundingArea.getHigherLongitudeValue(), 0.0);
        
        westLongitude = -5.0;
        eastLongitude = 10.0;
        northEast = Point.build(70, eastLongitude);
        southWest = Point.build(50, westLongitude);
        boundingArea = new BoundingArea(northEast, southWest);
        assertNotNull(boundingArea);
        assertEquals(eastLongitude, boundingArea.getHigherLongitudeValue(), 0.0);
        
        westLongitude = 170.0;
        eastLongitude = -110.0;
        northEast = Point.build(70, eastLongitude);
        southWest = Point.build(50, westLongitude);
        boundingArea = new BoundingArea(northEast, southWest);
        assertNotNull(boundingArea);
        assertEquals(westLongitude, boundingArea.getHigherLongitudeValue(), 0.0);
    }
    
}
