Java Geocalc ![alt text](https://api.travis-ci.org/grumlimited/geocalc.svg?branch=master "build")
=======

Geocalc is a simple java library aimed at doing arithmetics with Earth coordinates. 
It is designed to be simple to embed in your existing applications and easy to use. Geocalc can:

1. Calculate the distance between two coordinates
2. Find a point at X distance from a standpoint, given a bearing
3. Calculate a rectangular area around a point
4. Determine whether a Point is contained within that area
5. Calculate the azimuth and final bearings between two points

This library is being used on [www.rentbarometer.com](http://www.rentbarometer.com).

This library implements in Java lots of ideas from [Movable-Type](http://www.movable-type.co.uk/scripts/latlong.html). Many thanks.

## Installing

### Download

    git clone git@github.com:grumlimited/geocalc.git
    
### Compile
    
    mvn clean install -DskipTests=true

You will need a JDK 1.8 and maven.
    
### Embed

    <dependency>
        <groupId>com.grum</groupId>
        <artifactId>geocalc</artifactId>
        <version>0.5.1</version>
    </dependency>

*note: Geocalc is not available from Maven's public repo. You need to run the command above.* 

## Usage

### Creating a Point

    //Kew, London
    Coordinate lat = new DegreeCoordinate(51.4843774);
    Coordinate lng = new DegreeCoordinate(-0.2912044);
    Point kew = new Point(lat, lng);

### Converting between systems

Allows conversion of a coordinate between degrees, radians, D-M-s and GPS systems,

    double radians = degreeCoordinate.getRadianCoordinate().getRadians();
    
    double minutes = degreeCoordinate.getDMSCoordinate().getMinutes();
    double seconds = degreeCoordinate.getDMSCoordinate().getSeconds();
    double wholeDegrees = degreeCoordinate.getDMSCoordinate().getWholeDegrees();
    
    minutes = degreeCoordinate.getGPSCoordinate().getMinutes();
    seconds = degreeCoordinate.getGPSCoordinate().getSeconds(); // always 0
    wholeDegrees = degreeCoordinate.getGPSCoordinate().getWholeDegrees();
    
back and forth

    new DegreeCoordinate(-46.5456).getDMSCoordinate().getGPSCoordinate().getRadianCoordinate().decimalDegrees // getGPSCoordinate() implies loss of precision

### Distance between 2 points

#### Spherical law of cosines

    //Kew, London
    Coordinate lat = new DegreeCoordinate(51.4843774);
    Coordinate lng = new DegreeCoordinate(-0.2912044);
    Point kew = new Point(lat, lng);

    //Richmond, London
    lat = new DegreeCoordinate(51.4613418);
    lng = new DegreeCoordinate(-0.3035466);
    Point richmond = new Point(lat, lng);

    double distance = EarthCalc.getDistance(richmond, kew); //in meters
    
#### Harvesine formula

    //Kew, London
    Coordinate lat = new DegreeCoordinate(51.4843774);
    Coordinate lng = new DegreeCoordinate(-0.2912044);
    Point kew = new Point(lat, lng);

    //Richmond, London
    lat = new DegreeCoordinate(51.4613418);
    lng = new DegreeCoordinate(-0.3035466);
    Point richmond = new Point(lat, lng);

    double distance = EarthCalc.getHarvesineDistance(richmond, kew); //in meters
    
#### Vincenty formula
    
    //Kew, London
    Coordinate lat = new DegreeCoordinate(51.4843774);
    Coordinate lng = new DegreeCoordinate(-0.2912044);
    Point kew = new Point(lat, lng);

    //Richmond, London
    lat = new DegreeCoordinate(51.4613418);
    lng = new DegreeCoordinate(-0.3035466);
    Point richmond = new Point(lat, lng);

    double distance = EarthCalc.getVincentyDistance(richmond, kew); //in meters
    
    
### Finding a point at 'distance in meters away' from a standpoint, given a bearing

`otherPoint` will be 1000m away from Kew

    //Kew
    Coordinate lat = new DegreeCoordinate(51.4843774);
    Coordinate lng = new DegreeCoordinate(-0.2912044);
    Point kew = new Point(lat, lng);
    
    //Distance away point, bearing is 45deg
    Point otherPoint = EarthCalc.pointRadialDistance(kew, 45, 1000);
    
### BoundingArea

#### Calculating a rectangular area (BoundingArea) around a point

This is useful when, having a reference point, and a large set of 
other points, you need to figure out which ones are at most, say, 3000 meters away.

While this only gives an approximation, it is several order of magnitude faster
than calculating the distances from each point in the set to the reference point.

      BoundingArea area = EarthCalc.getBoundingArea(kew, 3000);
      Point nw = area.getNorthWest();
      Point se = area.getSouthEast();
      
Now, given that rectangle delimited by 'nw' and 'se', you can determine which points in your set are within these boundaries.

#### Determining whether a Point is contained within a BoundingArea

Now say you have a BoundingArea,

      //somewhere in Europe, not sure where ;-)
      Point northEast = new Point(new DegreeCoordinate(70), new DegreeCoordinate(145));
      Point southWest = new Point(new DegreeCoordinate(50), new DegreeCoordinate(110));
      BoundingArea boundingArea = new BoundingArea(northEast, southWest);
      
you can determine whether a point is contained withing that area using:
      
      Point point1 = new Point(new DegreeCoordinate(60), new DegreeCoordinate(120));
      assertTrue(boundingArea.isContainedWithin(point1)); //true
      
      Point point2 = new Point(new DegreeCoordinate(45), new DegreeCoordinate(120));
      assertFalse(boundingArea.isContainedWithin(point2)); //false

### Bearing between two points

#### Azimuth bearing - great circle path

    //Kew
    Coordinate lat = new DegreeCoordinate(51.4843774);
    Coordinate lng = new DegreeCoordinate(-0.2912044);
    Point kew = new Point(lat, lng);
    
    //Richmond, London
    lat = new DegreeCoordinate(51.4613418);
    lng = new DegreeCoordinate(-0.3035466);
    Point richmond = new Point(lat, lng);
    
    double bearing = EarthCalc.getBearing(kew, richmond); //in decimal degrees

#### Azimuth bearing - Vincenty formula

    //Kew
    Coordinate lat = new DegreeCoordinate(51.4843774);
    Coordinate lng = new DegreeCoordinate(-0.2912044);
    Point kew = new Point(lat, lng);
    
    //Richmond, London
    lat = new DegreeCoordinate(51.4613418);
    lng = new DegreeCoordinate(-0.3035466);
    Point richmond = new Point(lat, lng);
    
    double bearing = EarthCalc.getVincentyBearing(kew, richmond); //in decimal degrees
    
#### Final bearing - Vincenty formula

    //Kew
    Coordinate lat = new DegreeCoordinate(51.4843774);
    Coordinate lng = new DegreeCoordinate(-0.2912044);
    Point kew = new Point(lat, lng);
    
    //Richmond, London
    lat = new DegreeCoordinate(51.4613418);
    lng = new DegreeCoordinate(-0.3035466);
    Point richmond = new Point(lat, lng);
    
    double bearing = EarthCalc.getVincentyFinalBearing(kew, richmond); //in decimal degrees
