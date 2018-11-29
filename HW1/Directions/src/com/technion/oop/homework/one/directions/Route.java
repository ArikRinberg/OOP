package com.technion.oop.homework.one.directions;

import java.awt.Frame;
//import java.awt.Taskbar.Feature;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * A Route is a path that traverses arbitrary GeoSegments, regardless
 * of their names.
 * <p>
 * Routes are immutable. New Routes can be constructed by adding a segment 
 * to the end of a Route. An added segment must be properly oriented; that 
 * is, its p1 field must correspond to the end of the original Route, and
 * its p2 field corresponds to the end of the new Route.
 * <p>
 * Because a Route is not necessarily straight, its length - the distance
 * traveled by following the path from start to end - is not necessarily
 * the same as the distance along a straight line between its endpoints.
 * <p>
 * Lastly, a Route may be viewed as a sequence of geographical features,
 * using the <tt>getGeoFeatures()</tt> method which returns an Iterator of
 * GeoFeature objects.
 * <p>
 * <b>The following fields are used in the specification:</b>
 * <pre>
 *   start : GeoPoint            // location of the start of the route
 *   end : GeoPoint              // location of the end of the route
 *   startHeading : angle        // direction of travel at the start of the route, in degrees
 *   endHeading : angle          // direction of travel at the end of the route, in degrees
 *   geoFeatures : sequence      // a sequence of geographic features that make up this Route
 *   geoSegments : sequence      // a sequence of segments that make up this Route
 *   length : real               // total length of the route, in kilometers
 *   endingGeoSegment : GeoSegment  // last GeoSegment of the route
 * </pre>
 **/
public class Route 
{
	
 	// TODO Write abstraction function and representation invariant
	private double _totalLength;
	private LinkedList<GeoFeature> _geoFeatures;
	private LinkedList<GeoSegment> _geoSegments;
	

  	/**
  	 * Constructs a new Route.
     * @requires gs != null
     * @effects Constructs a new Route, r, such that
     *	        r.startHeading = gs.heading &&
     *          r.endHeading = gs.heading &&
     *          r.start = gs.p1 &&
     *          r.end = gs.p2
     **/
  	public Route(GeoSegment gs)
  	{
  		_totalLength = gs.getLength();
  		_geoFeatures = new LinkedList<GeoFeature>();
  		_geoFeatures.add(new GeoFeature(gs));
  		_geoSegments = new LinkedList<GeoSegment>();
  		_geoSegments.add(gs);
  		
  		assert checkRep();
  	}
  	
  	protected Route(Route source)
  	{
  		_totalLength = source._totalLength;
  		_geoFeatures = new LinkedList<GeoFeature>();
  		for (GeoFeature feature : source._geoFeatures) 
  		{
  			_geoFeatures.addLast(new GeoFeature(feature));
		}
  		_geoSegments = new LinkedList<GeoSegment>(source._geoSegments);
  		assert checkRep();
  	}

  	
  	private boolean checkRep()
  	{
  		int len = 0;
  		GeoPoint p = _geoFeatures.getFirst().getStart();
  		for (GeoFeature feature : _geoFeatures) {
			len += feature.getLength();
			assert feature.getStart().equals(p) :
				"features end point must be the same as next features start point";
			p = feature.getEnd();
		}
  		assert len == _totalLength : "sum of geoFeatures lenght must be equal to totalLength";
  		len = 0;
  		p = _geoSegments.getFirst().getP1();
  		for (GeoSegment segment : _geoSegments)
  		{
  			len += segment.getLength();
  			assert segment.getP1().equals(p) :
  				"segments end point must be the same as next segments start point";
  			p = segment.getP2();
  		}
  		assert len == _totalLength : "sum of geoSegments lenght must be equal to totalLength";
  		
  		Iterator<GeoSegment> segmentItr = _geoSegments.iterator();
  		for (GeoFeature feature : _geoFeatures) 
  		{
  			for (Iterator<GeoSegment> innerSegmentItr = feature.getGeoSegments(); innerSegmentItr.hasNext();)
  			{
  				assert innerSegmentItr.next() == segmentItr.next() :
  					"geoSegments must be identical to all geoFeaturess geoSegments apended together";
  				assert innerSegmentItr.hasNext() == segmentItr.hasNext();
  			}		
		}  	
  		return true;
  	}

    /**
     * Returns location of the start of the route.
     * @return location of the start of the route.
     **/
  	public GeoPoint getStart()
  	{
  		return _geoSegments.getFirst().getP1();
  	}


  	/**
  	 * Returns location of the end of the route.
     * @return location of the end of the route.
     **/
  	public GeoPoint getEnd() 
  	{
  		return _geoSegments.getLast().getP2();
  	}


  	/**
  	 * Returns direction of travel at the start of the route, in degrees.
   	 * @return direction (in compass heading) of travel at the start of the
   	 *         route, in degrees.
   	 **/
  	public double getStartHeading() 
  	{
  		return _geoSegments.getFirst().getHeading();
  	}


  	/**
  	 * Returns direction of travel at the end of the route, in degrees.
     * @return direction (in compass heading) of travel at the end of the
     *         route, in degrees.
     **/
  	public double getEndHeading()
  	{
  		return _geoSegments.getLast().getHeading();
  	}


  	/**
  	 * Returns total length of the route.
     * @return total length of the route, in kilometers.  NOTE: this is NOT
     *         as-the-crow-flies, but rather the total distance required to
     *         traverse the route. These values are not necessarily equal.
   	 **/
  	public double getLength()
  	{
  		return _totalLength;
  	}


  	/**
     * Creates a new route that is equal to this route with gs appended to
     * its end.
   	 * @requires gs != null && gs.p1 == this.end
     * @return a new Route r such that
     *         r.end = gs.p2 &&
     *         r.endHeading = gs.heading &&
     *         r.length = this.length + gs.length
     **/
  	public Route addSegment(GeoSegment gs) 
  	{
  		Route r = new Route(this);
  		r._totalLength += gs.getLength();
  		// if the new segment has the same name as last segment - add it to last gerFeature
  		if (gs.getName().equals(r._geoSegments.getLast().getName()))
  		{
  			GeoFeature temp = r._geoFeatures.getLast().addSegment(gs);
  			r._geoFeatures.removeLast();
  			r._geoFeatures.addLast(temp);
  		}
  		else // create new geoFeature in the end
  		{
  			r._geoFeatures.addLast(new GeoFeature(gs));
  		}
  		r._geoSegments.addLast(gs);
  		assert r.checkRep();
  		return r;
  	}


    /**
     * Returns an Iterator of GeoFeature objects. The concatenation
     * of the GeoFeatures, in order, is equivalent to this route. No two
     * consecutive GeoFeature objects have the same name.
     * @return an Iterator of GeoFeatures such that
     * <pre>
     *      this.start        = a[0].start &&
     *      this.startHeading = a[0].startHeading &&
     *      this.end          = a[a.length - 1].end &&
     *      this.endHeading   = a[a.length - 1].endHeading &&
     *      this.length       = sum(0 <= i < a.length) . a[i].length &&
     *      for all integers i
     *          (0 <= i < a.length - 1 => (a[i].name != a[i+1].name &&
     *                                     a[i].end  == a[i+1].start))
     * </pre>
     * where <code>a[n]</code> denotes the nth element of the Iterator.
     * @see homework1.GeoFeature
     **/
  	public Iterator<GeoFeature> getGeoFeatures() 
  	{
  		return _geoFeatures.iterator();
  	}


  	/**
     * Returns an Iterator of GeoSegment objects. The concatenation of the
     * GeoSegments, in order, is equivalent to this route.
     * @return an Iterator of GeoSegments such that
     * <pre>
     *      this.start        = a[0].p1 &&
     *      this.startHeading = a[0].heading &&
     *      this.end          = a[a.length - 1].p2 &&
     *      this.endHeading   = a[a.length - 1].heading &&
     *      this.length       = sum (0 <= i < a.length) . a[i].length
     * </pre>
     * where <code>a[n]</code> denotes the nth element of the Iterator.
     * @see homework1.GeoSegment
     **/
  	public Iterator<GeoSegment> getGeoSegments()
  	{
  		return _geoSegments.iterator();
  	}


  	/**
     * Compares the specified Object with this Route for equality.
     * @return true iff (o instanceof Route) &&
     *         (o.geoFeatures and this.geoFeatures contain
     *          the same elements in the same order).
     **/
  	public boolean equals(Object o)
  	{
  		if (o == null || !(o instanceof Route))
  		{
  			return false;
  		}
  		Route gf = (Route)o;

  		return _totalLength == gf._totalLength &&
  				_geoFeatures.equals(gf._geoFeatures) &&
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
    	int hash = (int)(_totalLength * 1000);
   
    	for (GeoFeature features : _geoFeatures)
    	{
			bits = features.hashCode() + i;
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
  		sb.append("route:\n");
  		for (GeoFeature feature : _geoFeatures)
  		{
			sb.append(feature.toString());
		}
  		sb.append("routes total leangth is: " + _totalLength + "\n");
  		return new String(sb);
  	}
}
