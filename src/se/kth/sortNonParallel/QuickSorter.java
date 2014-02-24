/**
 * Sorts an array with quick sort
 * 
 * @author	Helena Lindén & johan Stråle
 * @since	2014-02-24
 */

package se.kth.sortNonParallel;

public class QuickSorter {
	
	private float[] mArray;
	private int mLength;
	
	public void sort(float[] array) {
		mArray = array;
		mLength = mArray.length;
		
		if(mArray == null || mLength <=1)
			return;
		quicksort(0, mLength-1);
	}
		
	public void quicksort(int start, int stop) {
		
		if(stop-start <= 0)
			return;
		
		// find pivot (middle of start and stop)
		int pivotIndex = start + (stop-start)/2;
		float pivot = mArray[pivotIndex];
		int f=start, l=stop;
		
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
		
		quicksort(start, l);
		quicksort(f, stop);
	}

	private void swap(int f, int l) {
		float temp = mArray[l];
		mArray[l] = mArray[f];
		mArray[f] = temp;
	}
	
	public void print() {
		for(float nr: mArray) {
			System.out.println(nr);
		}	
	}
	
}
