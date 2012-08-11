Geocalc
=======

Helper classes to calculate Earth distances, bearing, etc.

This library is being used on [rentbarometer.com](http://rentbarometer.com).

How to use
-

### Distance between 2 coordinates

The result is given in meters.

    //Kew, London
    Coordinate lat = new DegreeCoordinate(51.4843774);
    Coordinate lng = new DegreeCoordinate(-0.2912044);
    Point kew = new Point(lat, lng);

    //Richmond, London
    lat = new DegreeCoordinate(51.4613418);
    lng = new DegreeCoordinate(-0.3035466);
    Point richmond = new Point(lat, lng);

    double distance = EarthCalc.getDistance(richmond, kew); //in meters
    
    
### Find a point at 'distance in meters away' from a standpoint, given a bearing

    //Kew
    Coordinate lat = new DegreeCoordinate(51.4843774);
    Coordinate lng = new DegreeCoordinate(-0.2912044);
    Point kew = new Point(lat, lng);
    
    //Distance away point, bearing is 45deg
    Point otherPoint = EarthCalc.pointRadialDistance(kew, 45, 0);
    
### Draw a rectangular area around a point

This is useful when, having a reference point, and a large set of 
other points, you need to figure out which ones are, say, 3000 meters away.

While this only gives an approximation, it is several order of magnitude faster
than calculating the distances from each point in the set to the reference point.

      BoundingArea area = EarthCalc.getBoundingArea(kew, 3000);
      Point nw = area.getNorthWest();
      Point se = area.getSouthEast();
      
Now, given that rectangle delimited by 'nw' and 'se', you can determine which points in your set are within these boundaries.

### Calculate bearing between two points

    //Kew
    Coordinate lat = new DegreeCoordinate(51.4843774);
    Coordinate lng = new DegreeCoordinate(-0.2912044);
    Point kew = new Point(lat, lng);
    
    //Richmond, London
    lat = new DegreeCoordinate(51.4613418);
    lng = new DegreeCoordinate(-0.3035466);
    Point richmond = new Point(lat, lng);
    
    double bearing = EarthCalc.getBearing(kew, richmond); //in decimal degrees
