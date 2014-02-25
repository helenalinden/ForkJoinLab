/**
 * Implementation for mergesort running on multiple cores
 * using fork/join
 * 
 * @author	Helena Lind��n & johan Str��le
 * @since	2014-02-25
 */

package se.kth.sortParallel;

import java.util.Arrays;
import java.util.concurrent.RecursiveAction;

import se.kth.test.PSorterTests;

public class MergeSorterTask extends RecursiveAction {

	private static final long serialVersionUID = 1L;
	private static final int THRESHOLD = PSorterTests.THRESHOLD_MERGE;
	private float[] mArray;
	private int mLength;
	private int mStart, mStop;

	public MergeSorterTask(float[] array, int start, int stop) {
		mArray = array;
		mLength = stop-start;
		mStart = start;
		mStop = stop;
	}

	@Override
	protected void compute() {

		if (mLength < THRESHOLD) {
			computeDirectly();
		} else {

			int middle = mStart + (mStop - mStart) / 2;

			MergeSorterTask ms1 = new MergeSorterTask(mArray, mStart, middle);
			MergeSorterTask ms2 = new MergeSorterTask(mArray, middle + 1, mStop);

			invokeAll(ms1, ms2);

			merge(mStart, middle, mStop);
		}
	}

	private void merge(int start, int middle, int stop) {
		
		float[] mTmpArray = new float[mLength+1];
		
		for (int i = start; i <= stop; i++)
			mTmpArray[i-start] = mArray[i];

		int f1 = start, f2 = middle + 1, orgPos = start;

		while (f1 <= middle && f2 <= stop) {
			if (mTmpArray[f2-start] <= mTmpArray[f1-start]) {
				mArray[orgPos] = mTmpArray[f2-start];
				f2++;
			} else {
				mArray[orgPos] = mTmpArray[f1-start];
				f1++;
			}
			orgPos++;
		}

		while (f1 <= middle) {
			mArray[orgPos] = mTmpArray[f1-start];
			f1++;
			orgPos++;
		}
	}

	private void computeDirectly() {
		Arrays.sort(mArray, mStart, mStop+1);
	}
}
