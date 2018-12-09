package com.technion.oop.homework.one.directions;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * A GeoFeature represents a route from one location to another along a
 * single geographic feature. GeoFeatures are immutable.
 * <p>
 * GeoFeature abstracts over a sequence of GeoSegments, all of which have
 * the same name, thus providing a representation for nonlinear or nonatomic
 * geographic features. As an example, a GeoFeature might represent the
 * course of a winding river, or travel along a road through intersections
 * but remaining on the same road.
 * <p>
 * GeoFeatures are immutable. New GeoFeatures can be constructed by adding
 * a segment to the end of a GeoFeature. An added segment must be properly
 * oriented; that is, its p1 field must correspond to the end of the original
 * GeoFeature, and its p2 field corresponds to the end of the new GeoFeature,
 * and the name of the GeoSegment being added must match the name of the
 * existing GeoFeature.
 * <p>
 * Because a GeoFeature is not necessarily straight, its length - the
 * distance traveled by following the path from start to end - is not
 * necessarily the same as the distance along a straight line between
 * its endpoints.
 * <p>
 * <b>The following fields are used in the specification:</b>
 * <pre>
 *   start : GeoPoint       // location of the start of the geographic feature
 *   end : GeoPoint         // location of the end of the geographic feature
 *   startHeading : angle   // direction of travel at the start of the geographic feature, in degrees
 *   endHeading : angle     // direction of travel at the end of the geographic feature, in degrees
 *   geoSegments : sequence	// a sequence of segments that make up this geographic feature
 *   name : String          // name of geographic feature
 *   length : real          // total length of the geographic feature, in kilometers
 * </pre>
 **/
public class GeoFeature
{
	//TODO:
	// Note 5 on the pdf
	
	// Implementation hint:
	// When asked to return an Iterator, consider using the iterator() method
	// in the List interface. Two nice classes that implement the List
	// interface are ArrayList and LinkedList. If comparing two Lists for
	// equality is needed, consider using the equals() method of List. More
	// info can be found at:
	//   http://docs.oracle.com/javase/8/docs/api/java/util/List.html
	
  	// Abstraction Function:
  	// A GeoFeature F is NaN if:
  	// 1. name = null
  	// 2. A segment in geoSegments is NaN
	// 3. Two segments in geoSegments have a different name
	// Otherwise F represents a feature which is built of different
	//  segments all with the same name, where for every consecutive segments
	//  s1 and s2 (s2 is after s1), s1.end = s2.start
  	
  	// Representation invariant for every GeoFeature F:
	// 1. name != null 
	// 2. all segments in geoSegments aren't NaN
  	// 3. all segments in geoSegments have the same name
	// 4. all consecutive segments s1 and s2 in geoSegments maintain that
	//     s1.end = s2.start
	
	final private String _name;
	final private double _totalLength;
	final private LinkedList<GeoSegment> _geoSegments;

	/**
     * Constructs a new GeoFeature.
     * @requires gs != null
     * @effects Constructs a new GeoFeature, r, such that
     *	        r.name = gs.name &&
     *          r.startHeading = gs.heading &&
     *          r.endHeading = gs.heading &&
     *          r.start = gs.p1 &&
     *          r.end = gs.p2
     **/
  	public GeoFeature(GeoSegment gs)
  	{
  		assert gs != null : "gs cannot be null";
 		
  		_name = gs.getName();
  		_geoSegments = new LinkedList<GeoSegment>();
  		_geoSegments.add(gs);
  		_totalLength = gs.getLength();
  		
  		assert checkRep();
  	}
 
  	
  	protected GeoFeature(GeoFeature source, GeoSegment gs)
  	{
  		_name = source._name;
  		_totalLength = source._totalLength + gs.getLength();
  		_geoSegments = new LinkedList<GeoSegment>(source._geoSegments);
  		_geoSegments.addLast(gs);
  		
  		assert checkRep();
  	}
  
  	
  	protected GeoFeature(GeoFeature source)
  	{
  		_name = source._name;
  		_totalLength = source._totalLength;
  		_geoSegments = new LinkedList<GeoSegment>(source._geoSegments);
  		
  		assert checkRep();
  	}
  	
  	
  	private boolean checkRep()
  	{
  		assert _geoSegments != null : "GeoFeature mast have GeoSegments";
  		
  		double len = 0;
  		GeoPoint p = getStart();
  		for (GeoSegment segment : _geoSegments)
  		{
  			len += segment.getLength();
  			assert segment.getName() == _name :
  				"all segments name must be same as GeoFeatures name";
  			assert segment.getP1().equals(p) : 
  				"segments end point must be the same as next segments start point";
  			p = segment.getP2();
		}
  		assert len == _totalLength :
  			"total length must be sum of all the segments lenght";
  		return true;
  	}
  

 	/**
 	  * Returns name of geographic feature.
      * @return name of geographic feature
      */
  	public String getName() 
  	{
  		return _name;
  	}


  	/**
  	 * Returns location of the start of the geographic feature.
     * @return location of the start of the geographic feature.
     */
  	public GeoPoint getStart() 
  	{
  		return _geoSegments.getFirst().getP1();
  	}


  	/**
  	 * Returns location of the end of the geographic feature.
     * @return location of the end of the geographic feature.
     */
  	public GeoPoint getEnd()
  	{
  		return _geoSegments.getLast().getP2();
  	}


  	/**
  	 * Returns direction of travel at the start of the geographic feature.
     * @return direction (in standard heading) of travel at the start of the
     *         geographic feature, in degrees.
     *         If the first geoSegment is of length 0, returns 0.
     */
  	public double getStartHeading()
  	{
  		return _geoSegments.getFirst().getHeading();
  	}


  	/**
  	 * Returns direction of travel at the end of the geographic feature.
     * @return direction (in standard heading) of travel at the end of the
     *         geographic feature, in degrees.
     *         If the last geoSegment is of length 0, returns 0.
     */
  	public double getEndHeading()
  	{
  		return _geoSegments.getLast().getHeading();
  	}


  	/**
  	 * Returns total length of the geographic feature, in kilometers.
     * @return total length of the geographic feature, in kilometers.
     *         NOTE: this is NOT as-the-crow-flies, but rather the total
     *         distance required to traverse the geographic feature. These
     *         values are not necessarily equal.
     */
  	public double getLength()
  	{
  		return _totalLength;
  	}


  	/**
   	 * Creates a new GeoFeature that is equal to this GeoFeature with gs
   	 * appended to its end.
     * @requires gs != null && gs.p1 = this.end && gs.name = this.name.
     * @return a new GeoFeature r such that
     *         r.end = gs.p2 &&
     *         r.endHeading = gs.heading &&
     *    	   r.length = this.length + gs.length
     **/
  	public GeoFeature addSegment(GeoSegment gs) 
  	{
  		return new GeoFeature(this, gs); 		
  	}


  	/**
     * Returns an Iterator of GeoSegment objects. The concatenation of the
     * GeoSegments, in order, is equivalent to this GeoFeature. All the
     * GeoSegments have the same name.
     * @return an Iterator of GeoSegments such that
     * <pre>
     *      this.start        = a[0].p1 &&
     *      this.startHeading = a[0].heading &&
     *      this.end          = a[a.length - 1].p2 &&
     *      this.endHeading   = a[a.length - 1].heading &&
     *      this.length       = sum(0 <= i < a.length) . a[i].length &&
     *      for all integers i
     *          (0 <= i < a.length-1 => (a[i].name == a[i+1].name &&
     *                                   a[i].p2d  == a[i+1].p1))
     * </pre>
     * where <code>a[n]</code> denotes the nth element of the Iterator.
     * @see homework1.GeoSegment
     */
  	public Iterator<GeoSegment> getGeoSegments() 
  	{
  		// TODO: Arik, what do you think, thats it?
  		return _geoSegments.iterator();
  	}


  	/**
     * Compares the argument with this GeoFeature for equality.
     * @return o != null && (o instanceof GeoFeature) &&
     *         (o.geoSegments and this.geoSegments contain
     *          the same elements in the same order).
     **/
  	public boolean equals(Object o)
  	{
  		if (o == null || !(o instanceof GeoFeature))
  		{
  			return false;
  		}
  		GeoFeature gf = (GeoFeature)o;
  		return _totalLength == gf._totalLength &&
  				_name.equals(gf._name) &&
  				_geoSegments.equals(gf._geoSegments);
  	}


  	/**
     * Returns a hash code for this.
     * @return a hash code for this.
     **/
  	public int hashCode() 
  	{
  		final int S = Integer.SIZE;
  		int bits, i = 0;
    	int hash = _name.hashCode();
   
    	for (GeoSegment segment : _geoSegments)
    	{
			bits = segment.hashCode() + i;
			// XOR after cyclic bit shift
			hash ^= (bits >>> i%S) | (bits << (S - i%S)); 
			i ++;
		}
    	return hash;
  	}


  	/**
  	 * Returns a string representation of this.
   	 * @return a string representation of this.
     **/
  	public String toString() 
  	{
  		StringBuffer sb = new StringBuffer();
  		sb.append("GeoFeature: " + _name + " ,leangth: "+ _totalLength + "\n");
  		for (GeoSegment segment : _geoSegments) {
			sb.append(segment.getP1() + " -> ");
		}
  		sb.append(getEnd() + "\n");
  		return new String(sb);
  	}
}
