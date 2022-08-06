package facebook.menu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Sorts {

	public static void main(String[] args) {
		String[] sArr = new String[] { "banana", "apple", "pizza", "chocolate" };
		Integer[] iArr = new Integer[] { 10, 5, 6, 8, 7, 4, 3, 1, 2, 9 };
		FacebookUser[] fArr = new FacebookUser[] { new FacebookUser("Galen", "password"),
				new FacebookUser("Zoey", "password"), new FacebookUser("Lexxi", "password"),
				new FacebookUser("Vahn", "password") };

		Collections.shuffle(Arrays.asList(sArr));
		Collections.shuffle(Arrays.asList(iArr));
		Collections.shuffle(Arrays.asList(fArr));

		insertionSort(sArr);
		insertionSort(iArr);
		insertionSort(fArr);

		Collections.shuffle(Arrays.asList(sArr));
		Collections.shuffle(Arrays.asList(iArr));
		Collections.shuffle(Arrays.asList(fArr));

		quickSort(sArr);
		quickSort(iArr);
		quickSort(fArr);

	}

	/** The method for sorting the numbers */
	public static <E extends Comparable<E>> void insertionSort(E[] list) { // Made insertionSort extend Comparable.
		for (int i = 1; i < list.length; i++) {
			/**
			 * insert list[i] into a sorted sublist list[0..i-1] so that list[0..i] is
			 * sorted.
			 */
			E currentElement = list[i]; // Replaced the double with E.
			System.out.println("Pass " + i + ": " + Arrays.toString(list)); // Instrumentation
			int k;
			for (k = i - 1; k >= 0 && list[k].compareTo(currentElement) > 0; k--) { // Added compareTo to support
																					// Generics.

				list[k + 1] = list[k];
				System.out.println("\tIndex: " + k + " " + Arrays.toString(list)); // Instrumentation
			}
			// Insert the current element into list[k+1]
			list[k + 1] = currentElement;
			System.out.println("End of Pass: " + i + " " + Arrays.toString(list)); // Instrumentation

		}
	}

	public static <E extends Comparable<E>> void quickSort(E[] list) {
		System.out.println("Starting quickSort on " + Arrays.toString(list));
		quickSort(list, 0, list.length - 1);

	}

	private static <E extends Comparable<E>> void quickSort(E[] list, int first, int last) {
		if (last > first) {
			int pivotIndex = first;
			pivotIndex = partition(list, pivotIndex, last);
			quickSort(list, first, pivotIndex - 1);
			quickSort(list, pivotIndex + 1, last);

		}

	}

	/** Partition the array list[first..last] */
	private static <E extends Comparable<E>> int partition(E[] list, int first, int last) {
		E pivot = list[first]; // Choose the first element as the pivot
		int low = first + 1; // Index for forward search
		int high = last; // Index for backward search

		System.out.println("\tPartitioning " + Arrays.toString(list) + " with pivot of " + pivot); // Prints partition
																									// and pivot we are
																									// pivoting on.

		while (high > low) {
			// Search forward from left
			while (low <= high && list[low].compareTo(pivot) <= 0) // Support for Generics.
				low++;

			System.out.println("\t\tLow index = " + low); // Instrumentation.

			// Search backward from right
			while (low <= high && list[high].compareTo(pivot) > 0) // Support for Generics.
				high--;
			System.out.println("\t\tHigh index = " + high);
			// Swap two elements in the list
			if (high > low) {
				System.out.println("\t\tSwapping " + list[low] + " and " + list[high]); // Instrumentation.
				E temp = list[high];
				list[high] = list[low];
				list[low] = temp;
			}
		}
		while (high > first && list[high].compareTo(pivot) >= 0) // Support for Generics.
			high--;
		System.out.println("\t\t Swapping pivot of " + pivot + " with " + list[high]);
		// Swap pivot with list[high]
		if (pivot.compareTo(list[high]) > 0) { // Support for Generics.
			list[first] = list[high];
			list[high] = pivot;
			return high;
		} else {
			return first;
		}
	}
}