package homework3.q3;

public class Integer extends Expression {
	int val;
	
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
