/**
 * Interface for sorting algorithms with strategy design pattern
 * 
 * @author	Helena Lindén & Johan Stråle
 * @since	2014-03-03
 */

package se.kth.sortParallel;

import java.util.concurrent.ForkJoinPool;

public interface Sorter {

	public long sort(ForkJoinPool pool);
	
	public void setArray(float array[]);
	
	public float[] getArray();
	
	public Sorter copy();
}
