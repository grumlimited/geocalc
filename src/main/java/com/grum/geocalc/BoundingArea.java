package com.grum.geocalc;


import org.apache.log4j.Logger;

/**
 * Represents an area, defined by its top left and bottom right
 * coordinates
 *
 * @author rgallet
 */
public class BoundingArea {
    Logger logger = Logger.getLogger(getClass());
    private Point northEast, southWest;
    private Point southEast, northWest;

    public BoundingArea(Point northEast, Point southWest) {
        this.northEast = northEast;
        this.southWest = southWest;

        southEast = new Point(new DegreeCoordinate(southWest.getLatitude()), new DegreeCoordinate(northEast.getLongitude()));
        northWest = new Point(new DegreeCoordinate(northEast.getLatitude()), new DegreeCoordinate(southWest.getLongitude()));
    }

    @Deprecated
    public Point getBottomRight() {
        logger.debug("getBottomRight() is deprecated. Use getSouthWest() instead.");
        return southWest;
    }

    @Deprecated
    public Point getTopLeft() {
        logger.debug("getTopLeft() is deprecated. Use getNorthEast() instead.");
        return northEast;
    }

    public Point getNorthEast() {
        return northEast;
    }

    public Point getSouthWest() {
        return southWest;
    }

    public Point getSouthEast() {
        return southEast;
    }

    public Point getNorthWest() {
        return northWest;
    }

    @Override
    public String toString() {
        return "BoundingArea{" + "northEast=" + northEast + ", southWest=" + southWest + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BoundingArea other = (BoundingArea) obj;
        if (this.northEast != other.northEast && (this.northEast == null || !this.northEast.equals(other.northEast))) {
            return false;
        }
        if (this.southWest != other.southWest && (this.southWest == null || !this.southWest.equals(other.southWest))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + (this.northEast != null ? this.northEast.hashCode() : 0);
        hash = 13 * hash + (this.southWest != null ? this.southWest.hashCode() : 0);
        return hash;
    }
}
