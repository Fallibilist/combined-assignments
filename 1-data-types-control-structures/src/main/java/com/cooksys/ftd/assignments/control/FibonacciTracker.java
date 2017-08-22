package com.cooksys.ftd.assignments.control;

/**
 * The FibonacciTracker class houses the three most recent Fibonacci numbers in a sequence
 * It is used by Fibonacci to calculate a specific Fibonacci number or the entire sequence
 * 
 * When using this class to instantiate an object we will be using the builder method, thus
 * there is no constructor other than the default constructor and each setter method returns
 * this object
 */
public class FibonacciTracker {
	private int currentFibNumber;
	private int oneBeforeCurrentFibNumber;
	private int twoBeforeCurrentFibNumber;
	
	/**
     * Get method for the current fibonacci number
     *
     * @return the current fibonacci number
     */
	public int getCurrentFibNumber() {
		return currentFibNumber;
	}

	/**
     * Get method for the previous fibonacci number
     *
     * @return the previous fibonacci number
     */
	public int getOneBeforeCurrentFibNumber() {
		return oneBeforeCurrentFibNumber;
	}

	/**
     * Get method for the number before the previous fibonacci number
     *
     * @return the number before the previous fibonacci number
     */
	public int getTwoBeforeCurrentFibNumber() {
		return twoBeforeCurrentFibNumber;
	}
	
	public FibonacciTracker setCurrentFibNumber(int currentFibNumber) {
		this.currentFibNumber = currentFibNumber;
		return this;
	}
	
	public FibonacciTracker setOneBeforeCurrentFibNumber(int oneBeforeCurrentFibNumber) {
		this.oneBeforeCurrentFibNumber = oneBeforeCurrentFibNumber;
		return this;
	}
	
	public FibonacciTracker setTwoBeforeCurrentFibNumber(int twoBeforeCurrentFibNumber) {
		this.twoBeforeCurrentFibNumber = twoBeforeCurrentFibNumber;
		return this;
	}
	
	public int sumLastTwoNumbers() {
		return oneBeforeCurrentFibNumber + twoBeforeCurrentFibNumber;
	}
}
