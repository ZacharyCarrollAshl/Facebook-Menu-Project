package AssignmentFive;
// Assign5Tester.java
// test client for ArrayListUtils.java

import java.io.*;
import java.util.*;

public class UtilitiesTester
{
	private static final PrintStream ERR = System.err;
	private static final PrintStream OUT = System.out;
	
	private static final Random GEN = new Random();
	private static final Scanner IN = new Scanner( System.in );
	
	/*
		test program:
			1. gets size and range limit for an ArrayList of random Integers
			2. creates an ArrayList of Integers (numbers) with random values in range 1...limit
			3. outputs the numbers list
			4. creates a new ArrayListUtils object (utils)
			5. calls utils' removeDuplicates with the numbers list
			6. outputs the numbers list (without duplicates)
			7. uses a sentinel loop that repeatedly (until user enters sentinel value of -1):
			7a. gets int value as user input
			7b. calls utils' linearSearch method with numbers list and int value
			7c. if search results in valid index (>= 0): value and index are output
			    otherwise: appropriate message is output
	 */
	public static void main(String[] args)
	{
		try
		{
			OUT.print("How many items in the list of numbers? ");
			int listSize = IN.nextInt();
			
			OUT.print("\nWhat is the limit of numbers list items? ");
			int listLimit = IN.nextInt();
			
			ArrayList<Integer> numbers = makeRandIntList(listSize, listLimit);
			
			OUT.println("\nnumbers list: " + numbers);
			
			Utilities<Integer> utils = new Utilities<Integer>();
			
			numbers = utils.removeDuplicates(numbers); // Set numbers to the list without duplicates
			
			OUT.println("\nnumbers list without duplicates: " + numbers);
			
			boolean done = false;
			
			while (!done)
			{
				OUT.print("\nEnter the next integer to search for (-1 to end): ");
				int value = IN.nextInt();
				
				if (value < 0)
				{
					done = true;
					continue;
				}
				
				int index = utils.linearSearch(numbers, value);
				
				if (index > -1)
				{
					OUT.printf("\n%d is at numbers[%d]\n", value, index);
				}
				else
				{
					OUT.printf("\n%d is not in the numbers list\n", value);
				}
			} // end while loop
		}
		catch (Exception ex)
		{
			ERR.println(ex);
		}
	} // end main method
	
	/*
		method is given integer size and integer range limit for
		  a list of Integers
		method returns an ArrayList of Integers with Integer objects
		  containing values between 1 and the given range limit
		method throws an IllegalArgumentException if either:
		  1. the given size is not positive
		  2. the given range limit is not positive 
	 */
	private static ArrayList<Integer> makeRandIntList(int size, int limit)
	{
		if (size <= 0)
		{
			throw new IllegalArgumentException("invalid size " + size);
		}
		if (limit <= 0)
		{
			throw new IllegalArgumentException("invalid limit " + limit);
		}
		
		ArrayList<Integer> result = new ArrayList<Integer>();
		
		for (int count=1; count<=size; count++)
		{
			result.add( GEN.nextInt(limit) + 1 );
		} // end for loop
		
		return result;
	} // end makeRandIntList method
} // end Assign5Tester class