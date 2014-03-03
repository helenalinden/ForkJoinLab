/**
 * Implementation for quicksort running on multiple cores
 * using fork/join
 * 
 * @author	Helena Lind��n & johan Str��le
 * @since	2014-02-25
 */

package se.kth.sortParallel;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class QuickSorterTask extends RecursiveAction implements Sorter{

	private static final long serialVersionUID = 1L;
	private int mThreshold;
	private float[] mArray;
	private int mLength;
	private int mStart, mStop;
	
	public QuickSorterTask(int threshold) {
		mThreshold = threshold;
	}

	public QuickSorterTask(float[] array, int start, int stop) {
		mArray = array;
		mLength = stop-start;
		mStart = start;
		mStop = stop;
	}
	
	@Override
	protected void compute() {
		
		if(mLength < mThreshold) {
			computeDirectly();
		} else {
			
			// find pivot (middle of start and stop)
			int pivotIndex = mStart + (mStop-mStart)/2;
			float pivot = mArray[pivotIndex];
			int f=mStart, l=mStop;
			
			while(f <= l) {
				
				while(mArray[f] < pivot)
					f++;
				
				while(mArray[l] > pivot)
					l--;
				
				if(f <= l) {
					swap(f, l);
					f++;
					l--;
				}
			}
			
			QuickSorterTask qs1 = new QuickSorterTask(mArray, mStart, l);
			QuickSorterTask qs2 = new QuickSorterTask(mArray, f, mStop);
			
			invokeAll(qs1, qs2);
		}
	}
	
	private void swap(int f, int l) {
		float temp = mArray[l];
		mArray[l] = mArray[f];
		mArray[f] = temp;
	}
	
	private void computeDirectly() {
		Arrays.sort(mArray, mStart, mStop+1);
	}

	@Override
	public long sort(ForkJoinPool pool) {
		
		long startTime = System.nanoTime();
		pool.invoke(this);
		return System.nanoTime() - startTime;
	}

	@Override
	public void setArray(float[] array) {
		mArray = array;
		mLength = mArray.length - 1;
		mStart = 0;
		mStop = mLength;
	}
	
	public void setThreshold(int threshold) {
		mThreshold = threshold;
	}

	@Override
	public float[] getArray() {
		return mArray;
	}

	@Override
	public Sorter copy() {
		return new QuickSorterTask(mThreshold);
	}
}
