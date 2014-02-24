/**
 * Tests quicksort, mergesort and Arrays.sort
 * 
 * @author	Helena Lindén & johan Stråle
 * @since	2014-02-24
 */
package se.kth.test;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Random;

import org.junit.Test;

import se.kth.sortNonParallel.MergeSorter;
import se.kth.sortNonParallel.QuickSorter;


public class SorterTests {

	public static final int SIZE = 1000000;
	public static final int MAX = 1000;
	public static final int TESTRUNS = 10;
	
	private float[] mArray;
	private int mLength;
	
	private void setUp() {
		mArray = new float[SIZE];
		Random generator = new Random();
		
		for(int i=0; i<SIZE; i++)
			mArray[i] = MAX*generator.nextFloat();
		
		mLength = mArray.length;
	}
	
	@Test
	public void testQuicksort() {
		long startTime, stopTime, totalTime = 0;
		
		setUp();
		QuickSorter qs = new QuickSorter();
		
		// Warm-up
		for(int i=0; i<3; i++) {
			setUp();
			qs.sort(mArray);
		}
		
		for(int i=0; i<TESTRUNS; i++) {
			setUp();
			// Force garbage collection
			System.gc();
			startTime = System.nanoTime();
			qs.sort(mArray);
			stopTime = System.nanoTime();
			totalTime+=(stopTime-startTime);
		}
		
		assertTrue(validate());
		
		System.out.println("Quicksort time: " + ((totalTime)*1E-6)/TESTRUNS + " mSec");
		
	}
	
	@Test
	public void testArraySort() {
	long startTime, stopTime, totalTime = 0;
		setUp();
		
		// Warm-up
		for(int i=0; i<3; i++) {
			setUp();
			Arrays.sort(mArray);
		}
		
		for(int i=0; i<TESTRUNS; i++) {
			setUp();
			// Force garbage collection
			System.gc();
			startTime = System.nanoTime();
			Arrays.sort(mArray);
			stopTime = System.nanoTime();
			totalTime+=(stopTime-startTime);
		}
		
		assertTrue(validate());
		
		System.out.println("Arrays sort time: " + ((totalTime)*1E-6)/TESTRUNS + " mSec");
	
	}
	
	@Test
	public void testMergeSort() {
		long startTime, stopTime, totalTime = 0;
		
		setUp();
		MergeSorter ms = new MergeSorter();
		
		// Warm-up
		for(int i=0; i<3; i++) {
			setUp();
			ms.sort(mArray);
		}
		
		for(int i=0; i<TESTRUNS; i++) {
			setUp();
			// Force garbage collection
			System.gc();
			startTime = System.nanoTime();
			ms.sort(mArray);
			stopTime = System.nanoTime();
			totalTime+=(stopTime-startTime);
		}
		
		assertTrue(validate());
		
		System.out.println("Mergesort time: " + ((totalTime)*1E-6)/TESTRUNS + " mSec");
	}
	
	private boolean validate() {
		for(int i = 0; i < mLength-1; i++) {
			if(mArray[i] > mArray[i+1])
				return false;
		}
		return true;
	}
}
