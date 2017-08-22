package com.cooksys.ftd.assignments.control;

/**
 * The Fibonacci sequence is simply and recursively defined: the first two elements are `1`, and
 * every other element is equal to the sum of its two preceding elements. For example:
 * <p>
 * [1, 1] =>
 * [1, 1, 1 + 1]  => [1, 1, 2] =>
 * [1, 1, 2, 1 + 2] => [1, 1, 2, 3] =>
 * [1, 1, 2, 3, 2 + 3] => [1, 1, 2, 3, 5] =>
 * ...etc
 */
public class Fibonacci {

    /**
     * Calculates the value in the Fibonacci sequence at a given index. For example,
     * `atIndex(0)` and `atIndex(1)` should return `1`, because the first two elements of the
     * sequence are both `1`.
     *
     * @param i the index of the element to calculate
     * @return the calculated element
     * @throws IllegalArgumentException if the given index is less than zero
     */
    public static int atIndex(int index) throws IllegalArgumentException {
    	FibonacciTracker tracker;
    	
    	if(index < 0) {
    		throw new IllegalArgumentException();
    	}
    	
        if(index == 0) {
        	return 1;
        }

        tracker = new FibonacciTracker();
    	tracker
    		.setCurrentFibNumber(0)
    		.setOneBeforeCurrentFibNumber(1)
    		.setTwoBeforeCurrentFibNumber(0);
        
        for(int incrementer = 0; incrementer < index; incrementer++) {
        	tracker.setCurrentFibNumber(tracker.sumLastTwoNumbers());
        	tracker.setTwoBeforeCurrentFibNumber(tracker.getOneBeforeCurrentFibNumber());
        	tracker.setOneBeforeCurrentFibNumber(tracker.getCurrentFibNumber());
        }
        		
        return tracker.getCurrentFibNumber();
    }

    /**
     * Calculates a slice of the fibonacci sequence, starting from a given start index (inclusive) and
     * ending at a given end index (exclusive).
     *
     * @param start the starting index of the slice (inclusive)
     * @param end   the ending index of the slice(exclusive)
     * @return the calculated slice as an array of int elements
     * @throws IllegalArgumentException if either the given start or end is negative, or if the
     *                                  given end is less than the given start
     */
    public static int[] slice(int start, int end) throws IllegalArgumentException {
        int[] fibSequence;
        
        if(start > end || end < 0) {
        	throw new IllegalArgumentException();
        }
        
        fibSequence = new int[end - start];
        
        for(int index = 0; index < fibSequence.length; index++) {
        	fibSequence[index] = atIndex(start);
        	start++;
        }
        
        return fibSequence;
    }

    /**
     * Calculates the beginning of the fibonacci sequence, up to a given count.
     *
     * @param count the number of elements to calculate
     * @return the beginning of the fibonacci sequence, up to the given count, as an array of int elements
     * @throws IllegalArgumentException if the given count is negative
     */
    public static int[] fibonacci(int count) throws IllegalArgumentException {
        return slice(0, count);
    }
}
