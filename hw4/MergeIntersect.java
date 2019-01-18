/*
 * MergeIntersect.java
 * 
 * Name: Mohao Yi
 * Email: mohaoyi@bu.edu
 * 
 * Description: This class implements a static method for finding the 
 * 				the intersect of two integer arrays.
 * 
 */
package hw4;

import java.util.Arrays;

public class MergeIntersect {
	
	// This method takes two integer arrays arr1 and arr2 as parameters, 
	// and uses an approach based on merging to find and return the 
	// intersection of the two arrays.
	public static int[] intersect(int[] arr1, int[] arr2) {
		
		int[] intersect;
		
		// determine the length of the intersect
		// note that the length of the intersect cannot be greater than 
		// the length of the shorter one of arr1 and arr2
		if (arr1.length < arr2.length) {
			intersect = new int[arr1.length];
		} else {
			intersect = new int[arr2.length];
		}
		
		// sort arr1 and arr2 using a more efficient sorting algorithm
		// so that later on, it will be easier to find the intersection
		Sort.mergeSort(arr1);
		Sort.mergeSort(arr2);
		
		// the index of the element in arr1 that is being looked at
		int id1 = 0;
		// the index of the element in arr2 that is being looked at
		int id2 = 0;
		// the index of the position in intersect to place the next element
		int i = 0;
		// This floating point is used to check duplication in the intersect, 
		// initialized as -0.5 so that it also works for the 1st iteration
		// It will be a double version of the latest element in the intersect.
		double prev = -0.5;
		
		// update the intersect, based on the fact that arr1 and arr2
		// are both sorted
		while ((id1 < arr1.length) && (id2 < arr2.length)) {
			// the process is separated into the following 3 cases
			
			// case 1: a common element of arr1 and arr2 is found
			if (arr1[id1] == arr2[id2]) {
				
				// if that common element is not repeated, 
				// arr1 กษ arr2 = current intersection + {common element}
				// + (arr1[id1+1:] กษ arr2[id2+1:])
				if (arr1[id1] != prev) {
					intersect[i] = arr1[id1];
					prev = intersect[i]; i++; 
				}
				
				// if that common element is repeated, 
				// arr1 กษ arr2 = current intersection
				// + (arr1[id1+1:] กษ arr2[id2+1:])
				id1++; id2++;
			
			// case 2: 
			} else if (arr1[id1] < arr2[id2]){
				// arr1 กษ arr2 = current intersection + 
				// (arr1[id1+1:] กษ arr2[id2:])
				id1++;
			
			// case 3: arr1[id1] > arr2[id2]
			} else {
				// arr1 กษ arr2 = current intersection + 
				// (arr1[id1:] กษ arr2[id2+1:])
				id2++;
				
			}
			
		}
		
		return intersect;
		
	}
	
	// tests for intersect:
	public static void main(String[] args) {
		int[] a1 = {10, 10, 10, 10, 10};
		int[] a2 = {10, 10, 10, 7};
		int[] result1 = MergeIntersect.intersect(a1, a2);
		System.out.println("result1 = " + Arrays.toString(result1));
		
		int[] a3 = {};
		int[] a4 = {2, 3, 3};
		int[] result2 = MergeIntersect.intersect(a3, a4);
		System.out.println("result2 = " + Arrays.toString(result2));
		
	}
	
}
