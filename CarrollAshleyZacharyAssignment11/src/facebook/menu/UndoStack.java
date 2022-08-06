package facebook.menu;

import java.util.Stack;

public class UndoStack {
	private static final Stack<UndoFunc> undoStack = new Stack<>(); // EXPLANATION : Creating new stack to get back the
																	// first option
																	// to be able to undo correctly with being able to
																	// undo things in the order they were done
																	// originally.

	public static void add(UndoFunc func) {
		undoStack.add(func); // Adding Lambda function to stack.
	}

	public static boolean hasUndo() {
		return undoStack.size() != 0; // Returns true if the undoStack size is not 0.
	}

	public static boolean undo() {
		if (undoStack.size() == 0) // If the stack's size equals Zero...
			return false; // Return false.

		UndoFunc func = undoStack.pop(); // Gets the first thing to undo off of the stack.
		if (func != null) { // If func doesn't equal null...
			return func.undo(); // It returns the result of the function.
		}
		return false; // Otherwise it returns false.
	}
}