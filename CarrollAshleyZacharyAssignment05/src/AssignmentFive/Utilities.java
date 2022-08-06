package AssignmentFive;
//Code made by Zachary Carroll-Ashley
//Creation Date: 2/20/2022
//Created for CIS 2217
import java.util.ArrayList;

public class Utilities<T> {

	public ArrayList<T> removeDuplicates(ArrayList<T> list) {
		if (list == null || list.isEmpty()) // If list is null/empty...
			return list; // Return the list.
		ArrayList<T> found = new ArrayList<T>(); // This sets up an ArrayList with the T type named Found.
		for (T element : list) { // Going through all of the elements in the ArrayList.
			if (!found.contains(element)) { // If the list found doesn't contain the element...
				found.add(element); // Add the element to the ArrayList Found.
			}
		}
		return found; // This returns the ArrayList Found.
	}

	public int linearSearch(ArrayList<T> haystack, T needle) {
		if (haystack == null || haystack.isEmpty()) // If Haystack is null/empty...
			return -1; // Return negative one to end the process.
		for (int index = 0; index < haystack.size(); index++) { // Creates the Index and sets to 0, loops until Index is
																// less than the size of Haystack, Then increases the
																// Index by one each loop.
			T element = haystack.get(index); // Sets up the T typed Element to get from the Index.
			if (element.equals(needle)) { // If Element equals Needle....
				return index; // Return the Index.
			}
		}
		return -1; // This returns negative One to return from the process.
	}
}
