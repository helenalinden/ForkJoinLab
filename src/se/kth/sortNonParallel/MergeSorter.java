/**
 * Sorts an array with merge sort
 * 
 * @author	Helena Lindén & johan Stråle
 * @since	2014-02-24
 */
package se.kth.sortNonParallel;

public class MergeSorter {
	
	private float[] mArray;
	private int mLength;
	private float[] mTmpArray;
	
	public void sort(float[] array) {
		mArray = array;
		mLength = mArray.length;
		mTmpArray = new float[mLength];
		
		if(mArray == null || mLength <=1)
			return;
		mergesort(0, mLength-1);
	}

	private void mergesort(int start, int stop) {
		if(stop-start <=0)
			return;
		
		int middle = start + (stop-start)/2;
		
		mergesort(start, middle);
		mergesort(middle+1, stop);
		merge(start, middle, stop);
	}

	private void merge(int start, int middle, int stop) {
		for(int i=start; i<=stop; i++)
			mTmpArray[i] = mArray[i];
		
		int f1 = start, f2 = middle + 1, orgPos = start;
		
		while(f1 <= middle && f2 <= stop) {
			if(mTmpArray[f2] <= mTmpArray[f1]) {
				mArray[orgPos] = mTmpArray[f2];
				f2++;
			}
			else {
				mArray[orgPos] = mTmpArray[f1];
				f1++;
			}
			orgPos++;
		}
		
		while(f1 <= middle) {
			mArray[orgPos] = mTmpArray[f1];
			f1++;
			orgPos++;
		}
	}
}
