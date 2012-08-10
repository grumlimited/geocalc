geocalc
=======

Helper classes to calculate Earth distances, bearing, etc.

How to use
=======

//Kew, London
Coordinate lat = new DegreeCoordinate(51.4843774);
Coordinate lng = new DegreeCoordinate(-0.2912044);
Point kew = new Point(lat, lng);

//Richmond, London
lat = new DegreeCoordinate(51.4613418);
lng = new DegreeCoordinate(-0.3035466);
Point richmond = new Point(lat, lng);

double distance = EarthCalc.getDistance(richmond, kew); //in meters