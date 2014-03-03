/**
 * Tests quicksort, mergesort running on multiple cores using Fork/Join
 * 
 * @author	Helena Lind��n & johan Str��le
 * @since	2014-02-25
 */

package se.kth.test;

import static org.junit.Assert.assertTrue;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;

import org.junit.Test;

import se.kth.sortParallel.MergeSorterTask;
import se.kth.sortParallel.QuickSorterTask;

public class PSorterTests {

	public static final int SIZE = (int) 1E7;
	public static final int MAX = (int) 1E4;
	public static final int TESTRUNS = 5;
	public static final int WARMUP = 3;
	public static final int CORE = 8;
	public static final int THRESHOLD_MERGE = (int) 12.5E6;
	public static final int THRESHOLD_QUICK = (int) 1E5;
	
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
		
		ForkJoinPool pool = new ForkJoinPool(CORE);
		
		
		// Warm-up
		for(int i=0; i<WARMUP; i++) {
			setUp();
			System.gc();
			QuickSorterTask qsTask = new QuickSorterTask(mArray, 0, mLength-1);
			qsTask.setThreshold(THRESHOLD_QUICK);
			pool.invoke(qsTask);
		}
		
		for(int i=0; i<TESTRUNS; i++) {
			setUp();
			// Force garbage collection
			QuickSorterTask qsTask = new QuickSorterTask(mArray, 0, mLength-1);
			qsTask.setThreshold(THRESHOLD_QUICK);
			System.gc();
			startTime = System.nanoTime();
			pool.invoke(qsTask);
			stopTime = System.nanoTime();
			totalTime+=(stopTime-startTime);
		}
		
		assertTrue(validate());
		
		System.out.println("Parallel Quicksort time: " + ((totalTime)*1E-6)/TESTRUNS + " mSec");
		
	}
	
	@Test
	public void testMergeSort() {
		long startTime, stopTime, totalTime = 0;
		
		setUp();
		ForkJoinPool pool = new ForkJoinPool(CORE);
		
		// Warm-up
		for(int i=0; i<WARMUP; i++) {
			setUp();
			System.gc();
			MergeSorterTask msTask = new MergeSorterTask(mArray, 0, mLength-1); 
			msTask.setThreshold(THRESHOLD_MERGE);
			pool.invoke(msTask);
		}
		
		for(int i=0; i<TESTRUNS; i++) {
			setUp();
			// Force garbage collection
			MergeSorterTask msTask = new MergeSorterTask(mArray, 0, mLength-1);
			msTask.setThreshold(THRESHOLD_MERGE);
			System.gc();
			startTime = System.nanoTime();
			pool.invoke(msTask);
			stopTime = System.nanoTime();
			totalTime+=(stopTime-startTime);
		}
		
		assertTrue(validate());
		
		System.out.println("Parallel Mergesort time: " + ((totalTime)*1E-6)/TESTRUNS + " mSec");
	}
	
	private boolean validate() {
		for(int i = 0; i < mLength-1; i++) {
			if(mArray[i] > mArray[i+1])
				return false;
		}
		return true;
	}
}
