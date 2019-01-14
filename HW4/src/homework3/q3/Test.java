package homework3.q3;

public class Test {

	public static void main(String[] args) {
		Expression e =
			new Multiplication(
				new Addition(
					new Double(2.5),
					new Double(3.5)),
				new UnaryMinus(
					new Integer(5)));
		System.out.println(e.eval()); // should print out -30.0
		System.out.println(e.toString()); // should print out ((2.5 + 3.5) * (-(5)))

	}

}
