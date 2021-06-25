package com.ph.services;


import com.ph.exceptions.CantDivideByZeroException;

public class Calculator {

	public int add(int a, int b) {
		return a + b;
	}

	public int substract(int a, int b) {
		return a - b;
	}

	public int divide(int a, int b) throws CantDivideByZeroException {
		if(b == 0) {
			throw new CantDivideByZeroException();
		}
		return a / b;
	}
	
	public int divide(String a, String b) {
		return Integer.valueOf(a) / Integer.valueOf(b);
	}
	
	public static void main(String[] args) {
		new Calculator().divide("a", "b");
	}

}
