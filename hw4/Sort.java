/*
 * Sort.java
 *
 * Computer Science 112, Boston University
 * 
 * 
 * *** Your solution should NOT go in this file. ***
 * *** It should go in SortCount.java instead. ***
 */

package hw4;

/**
 * Sort - a class containing implementations of various array-sorting
 * algorithms.  Each method takes an array of ints.  The methods
 * assume that the array is full.  They sort the array in place,
 * altering the original array.
 */
public class Sort {
    public static final int NUM_ELEMENTS = 10;
    
    /*
     * swap - swap the values of arr[a] and arr[b].
     * Used by several of the sorting algorithms below.
     */
    private static void swap(int[] arr, int a, int b) {
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }

    /*
     * Basic sorting algorithms
     *
     */
    
    /*
     * indexSmallest - returns the index of the smallest element
     * in the subarray from arr[start] to the end of the array.  
     * Used by selectionSort.
     */
    private static int indexSmallest(int[] arr, int start) {
        int indexMin = start;
        
        for (int i = start + 1; i < arr.length; i++) {
            if (arr[i] < arr[indexMin]) {
                indexMin = i;
            }
        }
        
        return indexMin;
    }
    
    /** selectionSort */
    public static void selectionSort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            int j = indexSmallest(arr, i);
            swap(arr, i, j);
        }
    }
    
    /** insertionSort */
    public static void insertionSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < arr[i-1]) {
                // Save a copy of the element to be inserted.
                int toInsert = arr[i];
                
                // Shift right to make room for element.
                int j = i;
                do {
                    arr[j] = arr[j - 1];
                    j = j - 1;
                } while (j > 0 && toInsert < arr[j-1]);
                
                // Put the element in place.
                arr[j] = toInsert;
            }
        }
    }
    
    /** bubbleSort */
    public static void bubbleSort(int[] arr) {
        for (int i = arr.length - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (arr[j] > arr[j+1]) {
                    swap(arr, j, j+1);
                }
            }
        }
    }

    /*
     * Divide and Conquer Sorting Algorithms
     *
     */
    
    /*
     * Quick Sort algorithm
     */
 
    /* partition - helper method for qSort */
    private static int partition(int[] arr, int first, int last) {
        int pivot = arr[(first + last)/2];
        int i = first - 1;  // index going left to right
        int j = last + 1;   // index going right to left
        
        while (true) {
            // moving from left to right, find an element >= the pivot
            do {
                i++;
            } while (arr[i] < pivot);
            
            // moving from right to left, find an element <= the pivot
            do {
                j--;
            } while (arr[j] > pivot); 
            
            // If the indices still haven't met or crossed,
            // swap the elements so that they end up in the correct subarray.
            // Otherwise, the partition is complete and we return j.
            if (i < j) {
                swap(arr, i, j);
            } else {
                return j;     // index of last element in the left subarray
            }
        }
    }
    
    /* qSort - recursive method that does the work for quickSort */
    private static void qSort(int[] arr, int first, int last) {
        int split = partition(arr, first, last);
        
        if (first < split) {
            qSort(arr, first, split);      // left subarray
        }
        if (last > split + 1) {
            qSort(arr, split + 1, last);   // right subarray
        }
    }
    
    /** quicksort */
    public static void quickSort(int[] arr) {
        qSort(arr, 0, arr.length - 1); 
    }

    /*
     * Merge Sort algorithm
     */ 
    
    /* merge - helper method for mergesort */
    private static void merge(int[] arr, int[] temp, 
      int leftStart, int leftEnd, int rightStart, int rightEnd)
    {
        int i = leftStart;    // index into left subarray
        int j = rightStart;   // index into right subarray
        int k = leftStart;    // index into temp
        
        while (i <= leftEnd && j <= rightEnd) {
            if (arr[i] < arr[j]) {
                temp[k] = arr[i];
                i++; k++;
            } else {
                temp[k] = arr[j];
                j++; k++;
            }
        }
        
        while (i <= leftEnd) {
            temp[k] = arr[i];
            i++; k++;
        }
        while (j <= rightEnd) {
            temp[k] = arr[j];
            j++; k++;
        }
        
        for (i = leftStart; i <= rightEnd; i++) {
            arr[i] = temp[i];
        }
    }
    
    /** mSort - recursive method for mergesort */
    private static void mSort(int[] arr, int[] temp, int start, int end) {
        if (start >= end) {
            return;
        }
        
        int middle = (start + end)/2;
        mSort(arr, temp, start, middle);
        mSort(arr, temp, middle + 1, end);
        merge(arr, temp, start, middle, middle + 1, end);
    }
    
    /** mergesort */
    public static void mergeSort(int[] arr) {
        int[] temp = new int[arr.length];
        mSort(arr, temp, 0, arr.length - 1);
    }
    
    /**
     * printArray - prints the specified array in the following form:
     * { arr[0] arr[1] ... }
     */
    public static void printArray(int[] arr) {
        System.out.print("{ ");
        
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        
        System.out.println("}");
    }
    
    public static void main(String[] arr) { 
        int[] orig = new int[NUM_ELEMENTS];
        for (int i = 0; i < NUM_ELEMENTS; i++) {
            orig[i] = (int)(50 * Math.random());
        }
        printArray(orig);
        
        int[] copy = new int[NUM_ELEMENTS];
        
        /* selection sort */
        System.arraycopy(orig, 0, copy, 0, orig.length); 
        selectionSort(copy);
        System.out.print("selection sort:\t");
        printArray(copy);
        
        /* insertion sort */
        System.arraycopy(orig, 0, copy, 0, orig.length); 
        insertionSort(copy);
        System.out.print("insertion sort:\t");
        printArray(copy);
        
        /* bubble sort */
        System.arraycopy(orig, 0, copy, 0, orig.length); 
        bubbleSort(copy);
        System.out.print("bubble sort:\t");
        printArray(copy);
        
        /* quicksort */
        System.arraycopy(orig, 0, copy, 0, orig.length); 
        quickSort(copy);
        System.out.print("quicksort:\t");
        printArray(copy);
        
        /* mergesort */
        System.arraycopy(orig, 0, copy, 0, orig.length); 
        mergeSort(copy);
        System.out.print("mergesort:\t");
        printArray(copy);
    }
}
