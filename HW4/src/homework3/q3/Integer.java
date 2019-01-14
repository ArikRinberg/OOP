package homework3.q3;

public class Integer implements Expression {
	
  	// Abstraction Function:
  	// A Integer I is a wrapper class for integer value
	//
	// Representation invariant:
  	//  none
	
	private final int val;
	
	public Integer(int val)
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
