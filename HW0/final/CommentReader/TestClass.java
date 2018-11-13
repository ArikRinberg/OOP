package com.technion.oop.homework.zero.commentreader;
/*
 * Comment on a class
 */
public class TestClass
{
	private int _i1; // A first number
	
	/* this explain a bit */ private int _i3; /* explain more */
	/* a comment */ // Another comment
	
	/**
	 * description for test class
	 */
	public TestClass()
	{
		_i1 = 1;
		_i3 = 1;
	}
	
	// Will this be fine /*
	public int sum()
	{
		// Returns a value
		return _i1 + _i3;
	}
	
	/* and another
	 * multi
	 * line /* explaining the line
	 */
}