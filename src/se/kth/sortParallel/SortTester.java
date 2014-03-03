/**
 * This class represent a testing api with fork/Join and strategy design pattern
 * 
 * @author	Helena Lindén & Johan Stråle
 * @since	2014-03-03
 */
package se.kth.sortParallel;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;

public class SortTester {
	
	private int mNrOfCores, mNrOfWarmups, mNrOfTestRuns, mArraySize, mRange; 
	private Sorter mSorter;
	private float[] mArray;
	
	public SortTester(int nrOfCores, int nrOfWarmups,
			int nrOfTestRuns, int arraySize, int range, Sorter sorter) {
		
		mNrOfCores = nrOfCores;
		mNrOfWarmups = nrOfWarmups;
		mNrOfTestRuns = nrOfTestRuns;
		mRange = range;
		mArraySize = arraySize;
		mSorter = sorter;
	}

	public long runSortTest() {

		long totalTime = 0;

		ForkJoinPool pool = new ForkJoinPool(mNrOfCores);

		// Warm-up
		for (int i = 0; i < mNrOfWarmups; i++) {
			
			System.gc();
			Sorter sorter = mSorter.copy();
			setUp(sorter);
			sorter.sort(pool);
		}

		for (int i = 0; i < mNrOfTestRuns; i++) {
			
			// Force garbage collection
			System.gc();
			Sorter sorter = mSorter.copy();
			setUp(sorter);
			totalTime += sorter.sort(pool);
			mArray = sorter.getArray();
		}
		
		return totalTime/mNrOfTestRuns;
	}

	private void setUp(Sorter sorter) {
		float[] array = new float[mArraySize];
		Random generator = new Random();

		for (int i = 0; i < mArraySize; i++)
			array[i] = mRange * generator.nextFloat();

		sorter.setArray(array);
	}
	
	public void printArray(Sorter sorter) {
		for(float nr : sorter.getArray())
			System.out.println(nr);
	}
	
	public float[] getArray() {
		return mArray;
	}
}
