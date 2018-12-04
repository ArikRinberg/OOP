package com.technion.oop.homework.one.directions;

/**
 * A GeoPoint is a point on the earth. GeoPoints are immutable.
 * <p>
 * North latitudes and east longitudes are represented by positive numbers.
 * South latitudes and west longitudes are represented by negative numbers.
 * <p>
 * The code may assume that the represented points are nearby the Technion.
 * <p>
 * <b>Implementation direction</b>:<br>
 * The Ziv square is at approximately 32 deg. 46 min. 59 sec. N
 * latitude and 35 deg. 0 min. 52 sec. E longitude. There are 60 minutes
 * per degree, and 60 seconds per minute. So, in decimal, these correspond
 * to 32.783098 North latitude and 35.014528 East longitude. The 
 * constructor takes integers in millionths of degrees. To create a new
 * GeoPoint located in the the Ziv square, use:
 * <tt>GeoPoint zivCrossroad = new GeoPoint(32783098,35014528);</tt>
 * <p>
 * Near the Technion, there are approximately 110.901 kilometers per degree
 * of latitude and 93.681 kilometers per degree of longitude. An
 * implementation may use these values when determining distances and
 * headings.
 * <p>
 * <b>The following fields are used in the specification:</b>
 * <pre>
 *   latitude :  real        // latitude measured in degrees
 *   longitude : real        // longitude measured in degrees
 * </pre>
 **/
public class GeoPoint
{	
	/** Minimum value the latitude field can have in this class. **/
	public static final int MIN_LATITUDE  =  -90 * 1000000;
	    
	/** Maximum value the latitude field can have in this class. **/
	public static final int MAX_LATITUDE  =   90 * 1000000;
	    
	/** Minimum value the longitude field can have in this class. **/
	public static final int MIN_LONGITUDE = -180 * 1000000;
	    
	/** Maximum value the longitude field can have in this class. **/
	public static final int MAX_LONGITUDE =  180 * 1000000;

  	/**
   	 * Approximation used to determine distances and headings using a
     * "flat earth" simplification.
     */
  	public static final double KM_PER_DEGREE_LATITUDE = 110.901;

  	/**
     * Approximation used to determine distances and headings using a
     * "flat earth" simplification.
     */
  	public static final double KM_PER_DEGREE_LONGITUDE = 93.681;
  	
	// Implementation hint:
	// Doubles and floating point math can cause some problems. The exact
	// value of a double can not be guaranteed except within some epsilon.
	// Because of this, using doubles for the equals() and hashCode()
	// methods can have erroneous results. Do not use floats or doubles for
	// any computations in hashCode(), equals(), or where any other time 
	// exact values are required. (Exact values are not required for length 
	// and distance computations). Because of this, you should consider 
	// using ints for your internal representation of GeoPoint. 

  	// Abstraction Function:
  	// A GeoPoint p is NaN if:
  	// 1. latitude isn't within the range [MIN_LATITUDE,MAX_LATITUDE]
  	// 2. longitude isn't within the range [MIN_LONGITUDE,MAX_LONGITUDE]
  	// Otherwise is a point (latitude, longitude) in millionths of a degree
  	
  	private final int _latitude;
	private final int _longitude;
  	
  	// Representation invariant for every GeoPoint p:
  	// 1. MIN_LATITUDE <= latitude <= MAX_LATITUDE
  	// 2. MIN_LONGITUDE <= longitude <= MAX_LONGITUDE  	
  	
  	/**
  	 * Constructs GeoPoint from a latitude and longitude.
     * @requires the point given by (latitude, longitude) in millionths
   	 *           of a degree is valid such that:
   	 *           (MIN_LATITUDE <= latitude <= MAX_LATITUDE) and
     * 	 		 (MIN_LONGITUDE <= longitude <= MAX_LONGITUDE)
   	 * @effects constructs a GeoPoint from a latitude and longitude
     *          given in millionths of degrees.
   	 **/
  	public GeoPoint(int latitude, int longitude) 
  	{ 		
  		_latitude = latitude;
  		_longitude = longitude;
  		assert checkRep();
  	}
  	
  	private boolean checkRep()
  	{
  		assert _latitude < MIN_LATITUDE :
  			"latitude cannot be smaller then " + MIN_LATITUDE;
  		assert _latitude > MAX_LATITUDE :
  			"latitude cannot be larger then " + MAX_LATITUDE;
  		assert _longitude < MIN_LONGITUDE :
  			"longitude cannot be smaller then " + MIN_LONGITUDE;
  		assert _longitude > MAX_LATITUDE :
  			"longitude cannot be larger then " + MAX_LONGITUDE;
  		return true;
  	}

  	 
  	/**
     * Returns the latitude of this.
     * @return the latitude of this in millionths of degrees.
     */
  	public int getLatitude()
  	{
  		return _latitude;
  	}


  	/**
     * Returns the longitude of this.
     * @return the latitude of this in millionths of degrees.
     */
  	public int getLongitude()
  	{
  		return _longitude;
  	}


  	/**
     * Computes the distance between GeoPoints.
     * @requires gp != null
     * @return the distance from this to gp, using the flat-surface, near
     *         the Technion approximation.
     **/
  	public double distanceTo(GeoPoint gp)
  	{
  		double latitudeDistance = GetLatitudeDistanceInKM(this, gp);
  		double longitudeDistance = GetLongitudeDistanceInKM(this, gp);
  		return Math.sqrt(Math.pow(latitudeDistance, 2) 
  				+ Math.pow(longitudeDistance, 2));
  	}


  	/**
     * Computes the compass heading between GeoPoints.
     * @requires gp != null && !this.equals(gp)
     * @return the compass heading h from this to gp, in degrees, using the
     *         flat-surface, near the Technion approximation, such that
     *         0 <= h < 360. In compass headings, north = 0, east = 90,
     *         south = 180, and west = 270.
     **/
  	public double headingTo(GeoPoint gp) 
  	{
		 //	Implementation hints:
		 // 1. You may find the mehtod Math.atan2() useful when
		 // implementing this method. More info can be found at:
		 // http://docs.oracle.com/javase/8/docs/api/java/lang/Math.html
		 //
		 // 2. Keep in mind that in our coordinate system, north is 0
		 // degrees and degrees increase in the clockwise direction. By
		 // mathematical convention, "east" is 0 degrees, and degrees
		 // increase in the counterclockwise direction. 
		
  		double latitudeDistance = GetLatitudeDistanceInKM(this, gp);
  		if ((gp._latitude - this._latitude) < 0)
  		{
  			latitudeDistance *= -1;
  		}
  		
  		double longitudeDistance = GetLongitudeDistanceInKM(this, gp);
  		if ((gp._longitude - this._longitude) < 0)
  		{
  			longitudeDistance *= -1;
  		}
  		
  		// Calculate theta in radians moving 0 degrees to north, and multiple by -1 to
  		//  reverse counterclockwise spin
  		double thetaInRadians = (Math.atan2(latitudeDistance, longitudeDistance) - Math.PI/2) * -1;
  		
  		// Convert to degrees
  		double thetaInDegrees = (thetaInRadians / Math.PI) * 180;
  		
  		// Edge case, 0.0*-1 = -0.0
  		if (thetaInDegrees == -0.0)
  		{
  			thetaInDegrees = 0.0;
  		}
  		
  		// Turn negative degrees to positive by adding 360 degrees
  		if (thetaInDegrees < 0)
  		{
  			thetaInDegrees += 360;
  		}
  		
  		return thetaInDegrees;
  	}


  	/**
     * Compares the specified Object with this GeoPoint for equality.
     * @return gp != null && (gp instanceof GeoPoint) &&
     * 		   gp.latitude = this.latitude && gp.longitude = this.longitude
     **/
  	public boolean equals(Object gp)
  	{
  		if (gp == null || !(gp instanceof GeoPoint))
  		{
  			return false;
  		}
  		GeoPoint convertedGp = (GeoPoint)gp;
  		return (convertedGp._latitude == _latitude) && (convertedGp._longitude == _longitude);
  	}

  	
  	/**
     * Returns a hash code value for this GeoPoint.
     * @return a hash code value for this GeoPoint.
   	 **/
  	public int hashCode()
  	{
    	return ( (_latitude >> 16) + (_latitude << 16) ) ^ _longitude;
  	}


  	/**
     * Returns a string representation of this GeoPoint.
     * @return a string representation of this GeoPoint.
     **/
  	public String toString() 
  	{
  		int latitudeDegrees = _latitude / 1000000;
  		int latitudeMillionthDegrees = _longitude % 1000000;
  		int longitudeDegrees = _longitude / 1000000;
  		int longitudeMillionthDegrees = _longitude % 1000000;
  		return "(" + latitudeDegrees + "." + latitudeMillionthDegrees + 
  				"," + longitudeDegrees + "." + longitudeMillionthDegrees + ")";
  	}

  	private static double GetLatitudeDistanceInKM(GeoPoint a, GeoPoint b)
  	{
  		return Math.abs(a.getLatitude() - b.getLatitude()) * KM_PER_DEGREE_LATITUDE / 1000000;
  	}
  	
  	private static double GetLongitudeDistanceInKM(GeoPoint a, GeoPoint b)
  	{
  		return Math.abs(a.getLongitude() - b.getLongitude()) * KM_PER_DEGREE_LONGITUDE / 1000000;
  	}
}
