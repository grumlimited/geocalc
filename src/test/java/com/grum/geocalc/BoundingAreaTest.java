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
        DegreeCoordinate northLatitude = DegreeCoordinate.newCoordinate(70.0);
        DegreeCoordinate eastLongitude = DegreeCoordinate.newCoordinate(145);
        Point northEast = Point.create(northLatitude, eastLongitude);
        
        DegreeCoordinate southLatitude = DegreeCoordinate.newCoordinate(50.0);
        DegreeCoordinate westLongitude = DegreeCoordinate.newCoordinate(110);
        Point southWest = Point.create(southLatitude, westLongitude);
        
        BoundingArea boundingArea = new BoundingArea(northEast, southWest);
        assertNotNull(boundingArea);
        assertEquals(northLatitude.getValue(), boundingArea.getNorthLatitude(), 0.0);
    }
    
    @Test
    public void testSouthLatitude() {
        DegreeCoordinate northLatitude = DegreeCoordinate.newCoordinate(70.0);
        DegreeCoordinate eastLongitude = DegreeCoordinate.newCoordinate(145);
        Point northEast = Point.create(northLatitude, eastLongitude);
        
        DegreeCoordinate southLatitude = DegreeCoordinate.newCoordinate(50.0);
        DegreeCoordinate westLongitude = DegreeCoordinate.newCoordinate(110);
        Point southWest = Point.create(southLatitude, westLongitude);

        BoundingArea boundingArea = new BoundingArea(northEast, southWest);
        assertNotNull(boundingArea);
        assertEquals(southLatitude.getValue(), boundingArea.getSouthLatitude(), 0.0);
    }
    
    
    @Test
    public void testGetWestLongitude() {
        DegreeCoordinate northLatitude = DegreeCoordinate.newCoordinate(70.0);
        DegreeCoordinate eastLongitude = DegreeCoordinate.newCoordinate(145);
        Point northEast = Point.create(northLatitude, eastLongitude);
        
        DegreeCoordinate southLatitude = DegreeCoordinate.newCoordinate(50.0);
        DegreeCoordinate westLongitude = DegreeCoordinate.newCoordinate(110);
        Point southWest = Point.create(southLatitude, westLongitude);

        BoundingArea boundingArea = new BoundingArea(northEast, southWest);
        assertNotNull(boundingArea);
        assertEquals(westLongitude.getValue(), boundingArea.getWestLongitude(), 0.0);
    }
    
    @Test
    public void testGetEastLatitude() {
        DegreeCoordinate northLatitude = DegreeCoordinate.newCoordinate(70.0);
        DegreeCoordinate eastLongitude = DegreeCoordinate.newCoordinate(145);
        Point northEast = Point.create(northLatitude, eastLongitude);
        
        DegreeCoordinate southLatitude = DegreeCoordinate.newCoordinate(50.0);
        DegreeCoordinate westLongitude = DegreeCoordinate.newCoordinate(110);
        Point southWest = Point.create(southLatitude, westLongitude);

        BoundingArea boundingArea = new BoundingArea(northEast, southWest);
        assertNotNull(boundingArea);
        assertEquals(eastLongitude.getValue(), boundingArea.getEastLongitude(), 0.0);
    }
    
}
