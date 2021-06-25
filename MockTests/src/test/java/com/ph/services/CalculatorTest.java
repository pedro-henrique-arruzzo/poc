package com.ph.services;

import com.ph.exceptions.CantDivideByZeroException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CalculatorTest {
	
	private Calculator calc;
	
	@Before
	public void setup(){
		calc = new Calculator();
	}

	@Test
	public void mustAddTwoValues(){
		//given
		int a = 5;
		int b = 3;
		
		//when
		int result = calc.add(a, b);
		
		//then
		Assert.assertEquals(8, result);
		
	}
	
	@Test
	public void deveSubtrairDoisValores(){
		//given
		int a = 8;
		int b = 5;
		
		//when
		int result = calc.substract(a, b);
		
		//then
		Assert.assertEquals(3, result);
		
	}
	
	@Test
	public void mustDivideTwoValues() throws CantDivideByZeroException {
		//given
		int a = 6;
		int b = 3;
		
		//when
		int result = calc.divide(a, b);
		
		//then
		Assert.assertEquals(2, result);
	}
	
	@Test(expected = CantDivideByZeroException.class)
	public void mustCastExceptionWhenDividedByZero() throws CantDivideByZeroException{
		int a = 10;
		int b = 0;
		
		calc.divide(a, b);
	}
	
	@Test
	public void mustDivide(){
		String a = "6";
		String b = "3";
		
		int result = calc.divide(a, b);
		
		Assert.assertEquals(2, result);
	}
}
