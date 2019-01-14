package homework3.q3;

public class Addition extends Expression {
	private Expression left;
	private Expression right;
	
	public Addition(Expression left, Expression right) {
		this.left = left;
		this.right = right;
	}
	
	@Override
	public double eval() {
		return left.eval() + right.eval();
	}

	@Override
	public String toString() {
		return "(" + left.toString() + " + " + right.toString() + ")";
	}
}
