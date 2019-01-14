package homework3.q3;

public class Double extends Expression{
	
	double val;
	
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
