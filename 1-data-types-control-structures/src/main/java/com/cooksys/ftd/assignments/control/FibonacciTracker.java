package com.cooksys.ftd.assignments.control;

/**
 * The FibonacciTracker class houses the three most recent Fibonacci numbers in a sequence
 * It is used by Fibonacci to calculate a specific Fibonacci number or the entire sequence
 * 
 * When using this class to instantiate an object we will be using the builder patterns, thus
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


	/**
     * Set method for the current fibonacci number
     *
     * @param a new value for the current fibonacci number
     * @return this FibonacciTracker object to implement the Builder pattern
     */
	public FibonacciTracker setCurrentFibNumber(int currentFibNumber) {
		this.currentFibNumber = currentFibNumber;
		return this;
	}

	/**
     * Set method for the previous fibonacci number
     *
     * @param a new value for the previous fibonacci number
     * @return this FibonacciTracker object to implement the Builder pattern
     */
	public FibonacciTracker setOneBeforeCurrentFibNumber(int oneBeforeCurrentFibNumber) {
		this.oneBeforeCurrentFibNumber = oneBeforeCurrentFibNumber;
		return this;
	}

	/**
     * Set method for the number before the previous fibonacci number
     *
     * @param a new value for the number before the previous fibonacci number
     * @return this FibonacciTracker object to implement the Builder pattern
     */
	public FibonacciTracker setTwoBeforeCurrentFibNumber(int twoBeforeCurrentFibNumber) {
		this.twoBeforeCurrentFibNumber = twoBeforeCurrentFibNumber;
		return this;
	}


	/**
     * Sums the last two fibonacci numbers
     *
     * @return the sum of the most recent two fibonacci numbers
     */
	public int sumLastTwoNumbers() {
		return oneBeforeCurrentFibNumber + twoBeforeCurrentFibNumber;
	}
}
