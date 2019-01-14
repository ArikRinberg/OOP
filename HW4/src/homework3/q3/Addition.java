package homework3.q3;

public class Addition implements Expression {
	
  	// Abstraction Function:
  	// A Addition A is NaN if:
  	// 1. left Expression is null or,
	// 2. right Expression is null
	// Otherwise A represents an addition between left value and right value

	//
	// Representation invariant:
  	// 1. left Expression isn't null
	// 2. right Expression isn't null
	
	private final Expression left;
	private final Expression right;
	
	public Addition(Expression left, Expression right) {
		if (left == null || right == null)
		{
			throw new NullPointerException();
		}
		
		this.left = left;
		this.right = right;
		
		assert checkRep();
	}
	
	@Override
	public double eval() {
		return left.eval() + right.eval();
	}

	@Override
	public String toString() {
		return "(" + left.toString() + " + " + right.toString() + ")";
	}
	
	private boolean checkRep()
	{
		return left != null && right != null;
	}
}
