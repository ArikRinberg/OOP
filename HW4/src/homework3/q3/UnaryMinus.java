package homework3.q3;

public class UnaryMinus implements Expression {
	
  	// Abstraction Function:
  	// A UnaryMinus U is NaN if:
  	// 1. Expression expression is null
	// Otherwise U represents a unary minus of expression

	//
	// Representation invariant:
  	// 1. Expression expression isn't null
	
	private final Expression expression;
	
	public UnaryMinus(Expression expression) {
		if (expression == null)
		{
			throw new NullPointerException();
		}
		
		this.expression = expression;
		
		assert checkRep();
	}
	
	@Override
	public double eval() {
		return  -1 * expression.eval();
	}
	
	@Override
	public String toString() {
		return "(-(" + expression.toString() + "))";
	}
	
	private boolean checkRep()
	{
		return expression != null;
	}
}
