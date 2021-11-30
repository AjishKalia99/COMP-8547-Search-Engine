package features;

import java.util.ArrayList;

public class MergeSort {
	// The Class of Implementing Merge Sort Algorithm in the Class.
	public void mergeSort(String[] a) {
		if (a.length >= 2) {
			String[] left = new String[a.length / 2];
			String[] right = new String[a.length - a.length / 2];
			
			for (int i = 0; i < left.length; i++) {
				left[i] = a[i];
	        }
			
			for (int i = 0; i < right.length; i++) {
				right[i] = a[i + a.length / 2];
	        }
 
			mergeSort(left);
	        mergeSort(right);

	        merge(a, left, right);        
        }
	}
	
    public void merge(String[] result, String[] left, String[] right) {
        int a = 0;
        int b = 0;
        for (int i = 0; i < result.length; i++) {
            if (b >= right.length || (a < left.length && left[a].compareToIgnoreCase(right[b]) < 0)) {
            	result[i] = left[a];
                a++;
            } 
            else {
            	result[i] = right[b];
                b++;
            }
        }
    }
}
