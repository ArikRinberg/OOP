public class BallsTest{
	
	public static final double EPSILON = 0.00000001;
	
	public static void main(String[] args)
	{
		BallContainer ballContainer = new BallContainer();
		
		Ball ball1 = new Ball(1.0);
		
		if (ballContainer.size() != 0)
		{
			System.out.println("Error creating a new Ball Container");
		}
		
		if (ballContainer.add(null))
		{
			System.out.println("Error adding ball, ball is null");
		}
		
		if (ballContainer.remove(null))
		{
			System.out.println("Error removing ball, ball is null");
		}
		
		
		if (!ballContainer.add(ball1))
		{
			System.out.println("Error adding ball, this ball is new");
		}
		
		Ball ball2 = new Ball(1.0);
		
		if (ballContainer.add(ball2))
		{
			System.out.println("Error adding ball, this ball is duplicate");
		}
		
		
		if (ballContainer.size() != 1)
		{
			System.out.println("Error in number of balls");
		}
		
		if (ballContainer.getVolume() != 1.0)
		{
			System.out.println("Error in volume");
		}
		
		ball1.setVolume(2.0);
		
		if (ballContainer.getVolume() != 1.0)
		{
			System.out.println("Error in volume, "
					+ "ball was changed and so was ballContainer");
		}
		
		ballContainer.clear();
		
		ballContainer.add(new Ball(0.0));
		ballContainer.add(new Ball(1.1));
		ballContainer.add(new Ball(3.2));
		
		if (Math.abs(ballContainer.getVolume() - 4.3) > EPSILON)
		{
			System.out.println("Error in volume, got " 
					+ ballContainer.getVolume());
		}
		
	}
}
