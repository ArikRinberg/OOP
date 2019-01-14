package homework3.q3;

public class UnaryMinus extends Expression {
	private Expression expression;
	
	public UnaryMinus(Expression expression) {
		this.expression = expression;
	}
	
	@Override
	public double eval() {
		return  -1 * expression.eval();
	}
	
	@Override
	public String toString() {
		return "(-(" + expression.toString() + "))";
	}
}
