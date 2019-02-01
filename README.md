Java Geocalc ![travis](https://travis-ci.org/grumlimited/geocalc.svg?branch=master "build")
=======

Geocalc is a simple java library aimed at doing arithmetics with Earth coordinates. 
It is designed to be simple to embed in your existing applications and easy to use. 

Geocalc can:

1. Calculate the distance between two coordinates (law of cosines, harvesine and vincenty)
2. Find a point at X distance from a standpoint, given a bearing
3. Calculate coordinates of a rectangular area around a point
4. Determine whether a Point is contained within that area
5. Calculate the azimuth, initial and final bearings between two points (vincenty)

This library is being used on [rentbarometer.com](https://www.rentbarometer.com).

This library implements in Java lots of ideas from [Movable-Type](http://www.movable-type.co.uk/scripts/latlong.html). Many thanks.

## Changelog

### 0.5.8
* Renamed in `EarthCalc.EARTH_DIAMETER` to `EarthCalc.EARTH_RADIUS`. Thanks [pradeepmurugesan](https://github.com/pradeepmurugesan)


### 0.5.7
* Bugfix in `EarthCalc.gcdDictance` [24](https://github.com/grumlimited/geocalc/issues/24). Thanks [dusiema](https://github.com/dusiema)

### 0.5.6
* Replaced `new BoundingArea(...)` with `BoundingArea.at`
* Bugfix in `EarthCalc.pointAt`. Normalising `λ2` so that bearing starts clockwise from north

### 0.5.5
* Renamed `getVincentyFinalBearing` to `vincentyFinalBearing`
* Updated copyright

### 0.5.4
* added `EarthCalc.midPoint(p1, p2)`
* renamed `EarthCalc.pointRadialDistance()` to `EarthCalc.pointAt(...)`
* renamed `BoundingArea.boundingArea()` to `BoundingArea.around(...)`
* removed `get...()` out of `Point` and `BoundingArea` 
* added maven javadoc plugin

### 0.5.3
* changed constructors to default and private visibility
* removed `get...()` keyword out of `EarthCalc` methods 
* `EarthCalc.getDistance()` is now `EarthCalc.gcdDistance()`
* renamed `BoundingArea.isContainedWithin(...)` to `BoundingArea.contains(...)`

### Embed

    <repositories>
        <repository>
            <id>jitpack.io</id>
            <url>https://jitpack.io</url>
        </repository>
    </repositories>


    <dependency>
	    <groupId>com.github.grumlimited</groupId>
	    <artifactId>geocalc</artifactId>
	    <version>0.5.8</version>
	</dependency>
	
Please refer to [jitpack.io/#grumlimited/geocalc/0.5.8](https://jitpack.io/#grumlimited/geocalc/0.5.8) for more information

## API

can be found here:

[grumlimited.co.uk/geocalc/0.5.8](http://www.grumlimited.co.uk/geocalc/0.5.8)

## Usage

### Creating a Point

    //Kew, London
    Coordinate lat = Coordinate.fromDegrees(51.4843774);
    Coordinate lng = Coordinate.fromDegrees(-0.2912044);
    Point kew = Point.at(lat, lng);

### Converting between systems

Allows conversion of a coordinate between degrees, radians, D-M-s and GPS systems,

    double radians = degreeCoordinate.toRadianCoordinate().radians;
    
    double minutes = degreeCoordinate.toDMSCoordinate().minutes;
    double seconds = degreeCoordinate.toDMSCoordinate().seconds;
    double wholeDegrees = degreeCoordinate.toDMSCoordinate().wholeDegrees;
    
    minutes = degreeCoordinate.toGPSCoordinate().minutes;
    seconds = degreeCoordinate.toGPSCoordinate().seconds; // always 0
    wholeDegrees = degreeCoordinate.toGPSCoordinate().wholeDegrees;
    
back and forth

    Coordinate.fromDegrees(-46.5456)
        .toDMSCoordinate()
        .toGPSCoordinate()
        .toRadianCoordinate()
        .decimalDegrees // toGPSCoordinate() implied loss of precision

### Distance between 2 points

#### Spherical law of cosines

    //Kew, London
    Coordinate lat = Coordinate.fromDegrees(51.4843774);
    Coordinate lng = Coordinate.fromDegrees(-0.2912044);
    Point kew = Point.at(lat, lng);

    //Richmond, London
    lat = Coordinate.fromDegrees(51.4613418);
    lng = Coordinate.fromDegrees(-0.3035466);
    Point richmond = Point.at(lat, lng);

    double distance = EarthCalc.gcdDistance(richmond, kew); //in meters
    
#### Harvesine formula

    //Kew, London
    Coordinate lat = Coordinate.fromDegrees(51.4843774);
    Coordinate lng = Coordinate.fromDegrees(-0.2912044);
    Point kew = Point.at(lat, lng);

    //Richmond, London
    lat = Coordinate.fromDegrees(51.4613418);
    lng = Coordinate.fromDegrees(-0.3035466);
    Point richmond = Point.at(lat, lng);

    double distance = EarthCalc.harvesineDistance(richmond, kew); //in meters
    
#### Vincenty formula
    
    //Kew, London
    Coordinate lat = Coordinate.fromDegrees(51.4843774);
    Coordinate lng = Coordinate.fromDegrees(-0.2912044);
    Point kew = Point.at(lat, lng);

    //Richmond, London
    lat = Coordinate.fromDegrees(51.4613418);
    lng = Coordinate.fromDegrees(-0.3035466);
    Point richmond = Point.at(lat, lng);

    double distance = EarthCalc.vincentyDistance(richmond, kew); //in meters
    
    
### Finding a point at 'distance in meters away' from a standpoint, given a bearing

`otherPoint` will be 1000m away from Kew

    //Kew
    Coordinate lat = Coordinate.fromDegrees(51.4843774);
    Coordinate lng = Coordinate.fromDegrees(-0.2912044);
    Point kew = Point.at(lat, lng);
    
    //Distance away point, bearing is 45deg
    Point otherPoint = EarthCalc.pointAt(kew, 45, 1000);
    
### BoundingArea

#### Calculating a rectangular area (BoundingArea) around a point

This is useful when, having a reference point, and a large set of 
other points, you need to figure out which ones are at most, say, 3000 meters away.

While this only gives an approximation, it is several order of magnitude faster
than calculating the distances from each point in the set to the reference point.

      BoundingArea area = EarthCalc.boundingArea(kew, 3000);
      Point nw = area.northWest;
      Point se = area.southEast;
      
Now, given that rectangle delimited by 'nw' and 'se', you can determine which points in your set are within these boundaries.

#### Determining whether a Point is contained within a BoundingArea

Now say you have a BoundingArea,

      //somewhere in Europe, not sure where ;-)
      Point northEast = Point.at(Coordinate.fromDegrees(70), Coordinate.fromDegrees(145));
      Point southWest = Point.at(Coordinate.fromDegrees(50), Coordinate.fromDegrees(110));
      BoundingArea boundingArea = BoundingArea.at(northEast, southWest);
      
you can determine whether a point is contained within that area using:
      
      Point point1 = Point.at(Coordinate.fromDegrees(60), Coordinate.fromDegrees(120));
      assertTrue(boundingArea.contains(point1)); //true
      
      Point point2 = Point.at(Coordinate.fromDegrees(45), Coordinate.fromDegrees(120));
      assertFalse(boundingArea.contains(point2)); //false

### Bearing between two points

#### Azimuth bearing - great circle path

    //Kew
    Coordinate lat = Coordinate.fromDegrees(51.4843774);
    Coordinate lng = Coordinate.fromDegrees(-0.2912044);
    Point kew = Point.at(lat, lng);
    
    //Richmond, London
    lat = Coordinate.fromDegrees(51.4613418);
    lng = Coordinate.fromDegrees(-0.3035466);
    Point richmond = Point.at(lat, lng);
    
    double bearing = EarthCalc.bearing(kew, richmond); //in decimal degrees

#### Azimuth bearing - Vincenty formula

    //Kew
    Coordinate lat = Coordinate.fromDegrees(51.4843774);
    Coordinate lng = Coordinate.fromDegrees(-0.2912044);
    Point kew = Point.at(lat, lng);
    
    //Richmond, London
    lat = Coordinate.fromDegrees(51.4613418);
    lng = Coordinate.fromDegrees(-0.3035466);
    Point richmond = Point.at(lat, lng);
    
    double bearing = EarthCalc.vincentyBearing(kew, richmond); //in decimal degrees
    
#### Final bearing - Vincenty formula

    //Kew
    Coordinate lat = Coordinate.fromDegrees(51.4843774);
    Coordinate lng = Coordinate.fromDegrees(-0.2912044);
    Point kew = Point.at(lat, lng);
    
    //Richmond, London
    lat = Coordinate.fromDegrees(51.4613418);
    lng = Coordinate.fromDegrees(-0.3035466);
    Point richmond = Point.at(lat, lng);
    
    double bearing = EarthCalc.vincentyFinalBearing(kew, richmond); //in decimal degrees

#### Mid point - This is the half-way point along a great circle path between the two points.

    //Kew
    Point kew = Point.at(Coordinate.fromDegrees(51.4843774), Coordinate.fromDegrees(-0.2912044));

    //Richmond, London
    Point richmond = Point.at(Coordinate.fromDegrees(51.4613418), Coordinate.fromDegrees(-0.3035466));
    
    Point midPoint = EarthCalc.midPoint(richmond, kew) // Point{latitude=51.47285976194266, longitude=-0.2973770580524634}
    
    