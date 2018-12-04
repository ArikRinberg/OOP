package com.technion.oop.homework.one.directions;

import java.awt.event.PaintEvent;

/**
 * A GeoSegment models a straight line segment on the earth. GeoSegments 
 * are immutable.
 * <p>
 * A compass heading is a nonnegative real number less than 360. In compass
 * headings, north = 0, east = 90, south = 180, and west = 270.
 * <p>
 * When used in a map, a GeoSegment might represent part of a street,
 * boundary, or other feature.
 * As an example usage, this map
 * <pre>
 *  Trumpeldor   a
 *  Avenue       |
 *               i--j--k  Hanita
 *               |
 *               z
 * </pre>
 * could be represented by the following GeoSegments:
 * ("Trumpeldor Avenue", a, i), ("Trumpeldor Avenue", z, i),
 * ("Hanita", i, j), and ("Hanita", j, k).
 * </p>
 * 
 * </p>
 * A name is given to all GeoSegment objects so that it is possible to
 * differentiate between two GeoSegment objects with identical
 * GeoPoint endpoints. Equality between GeoSegment objects requires
 * that the names be equal String objects and the end points be equal
 * GeoPoint objects.
 * </p>
 *
 * <b>The following fields are used in the specification:</b>
 * <pre>
 *   name : String       // name of the geographic feature identified
 *   p1 : GeoPoint       // first endpoint of the segment
 *   p2 : GeoPoint       // second endpoint of the segment
 *   length : real       // straight-line distance between p1 and p2, in kilometers
 *   heading : angle     // compass heading from p1 to p2, in degrees
 * </pre>
 **/
public class GeoSegment 
{	
  	// Abstraction Function:
  	// A GeoPoint p is NaN if:
  	// 1. name = null
  	// 2. p1 = null or p1 is NaN
	// 3. p2 = null or p2 is NaN
	// Otherwise represents a segment starting at p1 ending at p2 and
	//  which is called name
  	
  	// Representation invariant for every GeoSegment S:
	// 1. name != null
  	// 1. p1 != null
  	// 2. p2 != null
	
	
	private String _name;
	private GeoPoint _point1;
	private GeoPoint _point2;
	private double _heading;
	private double _length;
	
  	/**
     * Constructs a new GeoSegment with the specified name and endpoints.
     * @requires name != null && p1 != null && p2 != null
     * @effects constructs a new GeoSegment with the specified name and endpoints.
     **/
  	public GeoSegment(String name, GeoPoint p1, GeoPoint p2) 
  	{
  		_name = name;
  		_point1 = p1;
  		_point2 = p2;
  		_heading = _point1.headingTo(_point2);
  		_length = _point1.distanceTo(_point2);
  		
  		assert checkRep();
  	}
  	
  	private boolean checkRep()
  	{
  		assert !_name.isEmpty() : "GeoSegment must have a name";
  		assert _heading == _point1.headingTo(_point2);
  		assert _length == _point1.distanceTo(_point2);
  		return true;
  	}


  	/**
     * Returns a new GeoSegment like this one, but with its endpoints reversed.
     * @return a new GeoSegment gs such that gs.name = this.name
     *         && gs.p1 = this.p2 && gs.p2 = this.p1
     **/
  	public GeoSegment reverse() 
  	{
  		return new GeoSegment(_name, _point2, _point1);
  	}


  	/**
  	 * Returns the name of this GeoSegment.
     * @return the name of this GeoSegment.
     */
  	public String getName() 
  	{
  		return _name;
  	}


  	/**
  	 * Returns first endpoint of the segment.
     * @return first endpoint of the segment.
     */
  	public GeoPoint getP1() 
  	{
  		return _point1;
  	}


  	/**
  	 * Returns second endpoint of the segment.
     * @return second endpoint of the segment.
     */
  	public GeoPoint getP2() 
  	{
  		return _point2;
  	}


  	/**
  	 * Returns the length of the segment.
     * @return the length of the segment, using the flat-surface, near the
     *         Technion approximation.
     */
  	public double getLength()
  	{
  		return _length;
  	}


  	/**
  	 * Returns the compass heading from p1 to p2.
     * @return if this.length == 0 returns 0, 
     *         else returns the compass heading from p1 to p2, in degrees, using the
     *         flat-surface, near the Technion approximation.
     **/
  	public double getHeading() 
  	{
  		if(_length == 0)
  		{
  			return 0;
  		}
  		return _heading;
  	}


  	/**
     * Compares the specified Object with this GeoSegment for equality.
     * @return gs != null && (gs instanceof GeoSegment)
     *         && gs.name = this.name && gs.p1 = this.p1 && gs.p2 = this.p2
   	 **/
  	public boolean equals(Object obj)
  	{
  		if (obj == null || !(obj instanceof GeoSegment))
  		{
  			return false;
  		}
  		
  		GeoSegment gs = (GeoSegment)obj;
  		return _name.equals(gs._name) &&
  				_point1.equals(gs._point1) &&
  				_point2.equals(gs._point2);
  	}


  	/**
  	 * Returns a hash code value for this.
     * @return a hash code value for this.
     **/
  	public int hashCode() 
  	{
    	// This implementation will work, but you may want to modify it 
    	// for improved performance. 
    	return _name.hashCode() ^ _point1.hashCode() ^ 
    			((_point2.hashCode() >> 16) | (_point2.hashCode() << 16));
  	}


  	/**
  	 * Returns a string representation of this.
     * @return a string representation of this.
     **/
  	public String toString()
  	{
  		return "(\"" + _name + "\"," + _point1+ "," + _point2 + ")";
  	}

}

