package homework3.q3;

public class Double implements Expression{
	
  	// Abstraction Function:
  	// A Double D is a wrapper class for double value
	//
	// Representation invariant:
  	//  none
	
	private final double val;
	
	public Double(double val)
	{
		this.val = val;
	}
	
	@Override
	public double eval()
	{
		return val;
	}
	
	@Override
	public String toString()
	{
		return "" + val;
	}
}
