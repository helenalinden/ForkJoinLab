/**
 * This class tests sorting with strategy design pattern
 * 
 * @author	Helena Lindén & johan Stråle
 * @since	2014-03-03
 */
package se.kth.test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import se.kth.sortParallel.MergeSorterTask;
import se.kth.sortParallel.QuickSorterTask;
import se.kth.sortParallel.SortTester;

public class StrategyTests {

	public static final int SIZE = (int) 1E7;
	public static final int MAX = (int) 1E4;
	public static final int TESTRUNS = 5;
	public static final int WARMUP = 3;
	public static final int CORE = 8;
	public static final int THRESHOLD_MERGE = (int) 12.5E6;
	public static final int THRESHOLD_QUICK = (int) 1E5;
	
	@Test
	public void shouldTestQuicksortStrategy() {
		
		QuickSorterTask qsTask = new QuickSorterTask(THRESHOLD_QUICK);
		
		SortTester sTest = new SortTester(CORE, WARMUP, TESTRUNS, SIZE, MAX, qsTask);
		
		System.out.println("Quicksort time: " + sTest.runSortTest()*1E-6 + " ms");
		
		assertTrue(validate(sTest.getArray()));
	}
	
	@Test
	public void shouldTestMergesortStrategy() {
		
		MergeSorterTask msTask = new MergeSorterTask(THRESHOLD_MERGE);
		
		SortTester sTest = new SortTester(CORE, WARMUP, TESTRUNS, SIZE, MAX, msTask);
		
		System.out.println("Mergesort time: " + sTest.runSortTest()*1E-6 + " ms");
		
		assertTrue(validate(sTest.getArray()));
	}
	
	private boolean validate(float[] array) {
		for(int i = 0; i < array.length-1; i++) {
			if(array[i] > array[i+1])
				return false;
		}
		return true;
	}
	
	public void printArray(float[] array) {
		for(float nr : array)
			System.out.println(nr);
	}
}
