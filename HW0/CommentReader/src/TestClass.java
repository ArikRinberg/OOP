/*
 * Comment on a class
 */
public class TestClass {
	private int i1; // A first number
	private double d2; // Another number
	
	/* this explain a bit */ private int i3; /* explain more */
	/* a comment */ // Another comment
	
	/**
	 * description for test class
	 */
	public TestClass(double d)
	{
		i1 = 1;
		d2 = d;
		i3 = 1;
	}
	
	// Will this be fine /*
	public int sum()
	{
		// Returns a value
		return i1 + i3;
	}
	
	/* and another
	 * multi
	 * line /* explaining the line
	 */
}