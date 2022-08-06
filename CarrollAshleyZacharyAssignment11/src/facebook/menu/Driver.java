package facebook.menu;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.TreeSet;

public class Driver {
	private static final PrintStream Print = System.out; // Using this to outprint the menu.
	private static Scanner UserInput = new Scanner(System.in); // Scanner for UserInput.
	private static final Facebook fb = new Facebook();
	private static GraphViewer FBGraphViewer;
	private static final String FILENAME = "fBookUsers.dat"; // This sets up a String named FILENAME for the name of the
																// created file of Users that is written and read back
																// to get the Users for the menu.

	public Driver() {
	}

	public static int showMenu() {
		Print.println("Facebook Menu:"); // This prints out the menu.
		Print.println("1: List All Current Facebook Users Alphabetically"); // This prints out the menu.
		Print.println("2: List All Current Facebook Users By Number Of Friends"); // This prints out the menu.
		Print.println("3: Add a Facebook User"); // This prints out the menu.
		Print.println("4: Delete A Facebook User"); // This prints out the menu.
		Print.println("5: Get a User's Password Hint"); // This prints out the menu.
		Print.println("6: Add a friend,"); // This prints out the menu.
		Print.println("7: Unadd a friend,"); // This prints out the menu.
		Print.println("8: List User's Friends,"); // This prints out the menu.
		Print.println("9: Get friend recommendations,"); // This prints out the menu.
		Print.println("10: Like something"); // This prints out the menu.
		Print.println("11: Show what User's have liked Alphabetically"); // This prints out the menu.
		Print.println("12: Undo User's actions"); // This prints out the menu.
		Print.println("13: Display graph"); // This prints out the menu.
		Print.println("14: Quit"); // This prints out the menu.

		return UserInput.nextInt(); // This reads the User's input using the before mentioned scanner.
	}

	public static void main(String[] args) {
		readUsers(); // This calls the readUsers method which reads in
		while (true) {
			try {
				int Menu = showMenu();
				UserInput.nextLine(); // Throw away the return
				switch (Menu) { // Each switch is one of the menu's options. It tells the menu to try and run
								// the methods in the cases when the option is chosen by the user with the above
								// used Scanner asking for their input through a choice.
				case 1: {
					listUsersAlphabetically(); // This is the function telling the menu to print the ArrayList but the
												// User's ArrayList is sorted Alphabetically.
					break;
				}
				case 2:
					listUsersByFriends(); // This is the function telling the menu to print the ArrayList but the User's
											// ArrayList is sorted who has the largest amount of friends.
					break;
				case 3: {
					addUser(); // This tells the menu to try and add the User to the ArrayList.
					break;
				}
				case 4: {
					deleteUser(); // This tells the menu to try and delete the User from the ArrayList.
					break;
				}
				case 5: {
					getPasswordHint(); // This tells the menu to try and retrieve the User's PasswordHint.
					break;
				}
				case 6: {
					addFriend(); // This tells the menu to try and add a friend to the inputted User.
					break;
				}
				case 7: {
					removeFriend(); // This tells the menu to try and remove a friend to the inputted User.
					break;
				}
				case 8: {
					showFriends(); // This tells the menu to try and show the friends of the inputted User.
					break;
				}

				case 9: {
					recommendFriends(); // This tells the menu to try and show the recommended friends of the inputted
										// User to the inputted User.
					break;
				}

				case 10: {
					LikeThings(); // TO DO: Make function so the User can like a string. First prompt for the
									// User's Username & Password, then prompt for the String that the User wants
									// to like. Update the data structure and then provide a response to the User
									// indicating that the User's like has been noted unless they already liked that
									// then make sure that the User liking that again has no effects.
					break;
				}

				case 11: {
					listUsersLikesAlphabetically(); // TO DO: Create function that lists the User's likes in
													// alphabetical
					// order.
					break;
				}

				case 12: {
					UndoUserAction(); // TO DO: Create function that lists the User's likes in
										// alphabetical
					// order.
					break;
				}

				case 13: {
					DisplayGraph(); // This tells the menu to break and stop working.
					break;
				}
				case 14: {
					Quit(); // This tells the menu to break and stop working.
					return;
				}
				}
			}

			catch (Exception e) {
				// This error will show if something didn't work in the code and will tell
				// exactly which line of code was the reason for it breaking.
				e.printStackTrace();
			}
		}
	}

	private static void DisplayGraph() {
		FacebookUser User = getValidatedUser(); // This automatically asks for the User's password to validate.
		if (User == null) // If the User is null...
			return; // It returns.
		System.out.println("What depth? "); // Asks User for the correct depth they'd like to use.
		int maxLevel = UserInput.nextInt(); // Sets the max level of depth to the user's depth given.
		if (maxLevel < 0) { // If the maxLevel of depth is less than 0...
			System.out.println("Value must be non-negative."); // Tells the User that depth must be a non-negative
																// value.
			return; // Returns.
		}
		System.out.println("Displaying graph for " + User.toString()); // Prints a message for displaying the graph of
																		// the User to the console.
		FBGraphViewer = new GraphViewer(User, maxLevel); // Shows the graph.

	}

	private static void UndoUserAction() {
		FacebookUser User = getValidatedUser(); // This automatically asks for the User's password to validate.
		if (User == null)
			return;

		if (!UndoStack.hasUndo()) { // If UndoStack doesn't have actions to undo...
			System.out.println("There is currently nothing that is undoable, try again! :P"); // Prints out this error
																								// message and returns.
			return;
		}
		if (UndoStack.undo()) { // If UndoStack has undoable actions...
			System.out.println("Undone!"); // If successful, prints out a message indicating it succeed.
		} else { // If it failed...
			System.out.println("Whoops, something broke!"); // Prints out an error message telling you something broke.
		}
	}

	private static void LikeThings() {
		FacebookUser User = getValidatedUser(); // Gets User and checks Password.
		if (User == null) // If User equals null...
			return; // Returns.

		System.out.println("What do you wish to like?"); // Asks the User what they would wish to like.
		String like = UserInput.nextLine(); // Uses the UserInput scanner to read in the User's Like as a String.

		if (like == null || like.isEmpty()) { // If Like is null or Like is empty...
			System.out.println("Like is empty, please try again."); // Returns a message telling the User to try again.
			return; // Returns.
		}
		if (User.likes(like)) { // If User likes Like already...
			System.out.println("Already liked that, try again!"); // Returns a message telling the User that they cannot
																	// like something they've already liked using the
																	// program.
			return; // Returns.
		}
		User.addLike(like); // Adds the User's like.
		System.out.println(like + " added"); // Prints out a message telling the User their like was added.

	}

	private static void listUsersLikesAlphabetically() {
		FacebookUser User = getValidatedUser(); // Gets User and checks Password.
		if (User == null) // If User equals null...
			return; // Returns

		System.out.println(User.getName() + " likes:"); // Tells the User what they like.
		TreeSet<String> likes = User.getLikes(); // Sets up a TreeSet of the User's likes.
		for (String like : likes) { // Looping through the Likes.
			System.out.println(like); // Returns a message telling the User their Like.
		}
	}

	private static void listUsersByFriends() {
		for (FacebookUser user : fb.getUsersByFriendCount()) // This calls the method getUsersByFriendCount to sort the
																// Users list by amount of friends.
			System.out.println(user.getName()); // This prints out the User's name.

	}

	private static FacebookUser getValidatedUser() {
		System.out.println("Please, enter your Facebook Username: "); // This asks for the User's Username.
		String UName = UserInput.nextLine(); // This sets the given Username to a String.
		FacebookUser User = fb.getUserByName(UName); // This gets the User by their name.
		if (User == null) { // If the Username String is null...
			System.out.println("Sorry, that isn't a correct Facebook Username."); // It tells you this isn't a valid
																					// Facebook Username.
			return null; // This returns back to the menu.
		}
		System.out.println("Please, enter your password: "); // This asks for a Password from the User.
		String PWord = UserInput.nextLine(); // This sets the Password to a String.
		if (!User.checkPassword(PWord)) { // If the User's Password isn't correct...
			System.out.println("Wrong password! Please try again! :P"); // It tells you to try again with a correct
																		// Password for said User.
			return null; // This returns back to the menu.
		}
		return User;
	}

	private static void listUsersAlphabetically() {
		for (FacebookUser user : fb.getUsersAlphabetically()) // Uses the getUsersAlphabetically method in the Facebook
																// class to sort the
																// Users Alphabetically.
			System.out.println(user.getName()); // This prints out the User's name.
	}

	private static void recommendFriends() {
		FacebookUser User = getValidatedUser(); // Gets User and checks Password.
		if (User == null) // If User equals null...
			return; // Returns.
		for (FacebookUser recUser : User.getRecommendedFriends()) {
			System.out.println(recUser.getName());
		}
	}

	private static void showFriends() throws CloneNotSupportedException {
		System.out.println("Please, enter your Facebook Username: "); // This asks the User for their Username.
		String UName = UserInput.nextLine(); // This sets the given Username to a Username String.
		FacebookUser User = fb.getUserByName(UName); // This gets the User by their name just like the method suggests.
		if (User == null) { // If the User equals Null...
			System.out.println("Sorry, that isn't a correct Facebook Username."); // It prints out this error message
																					// telling the User the inputted
																					// User isn't correct.
			return; // This returns back to the menu.
		}
		System.out.println("Please, enter your password: "); // This asks the User for their Password to put to a
																// String.
		String PWord = UserInput.nextLine(); // This puts the User's Password to a Password String.
		if (!User.checkPassword(PWord)) { // This checks the Password given on the String to the actual Password for the
											// User.
			System.out.println("Wrong password! Please try again! :P"); // If not, this outputs an error message asking
																		// the User to try again.
			return; // This returns back to the menu.
		}
		for (FacebookUser friend : User.getFriends()) {
			System.out.println(friend); // This pritns out the User's Friends.
		}
	}

	private static void removeFriend() {
		FacebookUser User = getValidatedUser(); // Gets User and checks Password.
		if (User == null) // If User equals null...
			return; // Returns.
		System.out.println("Please, enter who you would like to defriend: "); // This asks the User which friend they
																				// would like to remove.
		String FriendName = UserInput.nextLine(); // This sets that User to a String.
		FacebookUser Friend = fb.getUserByName(FriendName); // This gets the User by their name.
		if (Friend.equals(User)) { // If the Friend you are trying to unadd equals your own Username...
			System.out.println("You cannot unadd yourself as a friend! Stop it, get some help."); // This outprints an
																									// error message
																									// telling you that
																									// you cannot unadd
																									// yourself as your
																									// not your own
																									// friend.
			return; // This returns back to the menu.
		}
		User.defriend(Friend); // This defriends the User asked to be defriended from the User's friend list.
	}

	private static void addFriend() {
		FacebookUser User = getValidatedUser();
		if (User == null)
			return;
		System.out.println("Please, enter who you would like to friend: "); // This asks who you would like to add as a
																			// Friend.
		String FriendName = UserInput.nextLine(); // This sets that User to a String of their own.
		FacebookUser Friend = fb.getUserByName(FriendName); // This gets the User that the is going to be added as your
															// Friend by their UserName.

		if (Friend.equals(User)) { // If the requested Friend is your own Username...
			System.out.println("You cannot add yourself as a friend! Stop it, get some help."); // This tells you that
																								// you cannot add
																								// yourself because it
																								// is impossible to add
																								// yourself since you
																								// cannot be your own
																								// Friend.
			return; // This returns back to the menu.
		}
		User.friend(Friend); // This adds the User to your Friend's list.
	}

	private static void Quit() { // This method breaks the menu and prints out the message "Goodbye" to the User
									// in the console.
		System.out.println("See ya later!"); // This prints out a message telling the User Goodbye when the program
												// closes.
		writeUsers(); // This runs the Method to write all of the processes done such as; adding new
						// Users, Deleting Users, etc etc to the fBookUsers.dat file.
		if (FBGraphViewer != null) // If FBGraphViewer isn't null...
			FBGraphViewer.dispose(); // Cleans up after it.
	}

	private static void getPasswordHint() { // This method helps to get the Password Hint for the User entered.
		System.out.println("Enter UserName of User you'd like to see the PasswordHint of: "); // This asks for the
																								// Username you'd like
																								// to get the Hint for
																								// their Password.

		String userName = UserInput.nextLine(); // This prints said Username to a String.
		FacebookUser user = fb.getUserByName(userName); // This gets the User by their given Username.
		if (user == null) { // If the Username is null...
			System.out.println("User doesn't exist!"); // This tells you the User doesn't exist.
			return; // This returns back to the menu.
		}
		System.out.println("Password Hint: " + user.passwordHint); // If the User is correct to someone on the User's
																	// list, it prints out their Password Hint for you.
	}

	private static void deleteUser() { // This functions as the way to delete User's off of the ArrayList.
		System.out.println("Which user would you like to delete? : ");

		if (UserInput.hasNextLine()) {
			String UserToDelete = UserInput.nextLine(); // The user was asked for which user they would like deleted, so
														// this
														// creates it as a string.
			if (UserToDelete.isEmpty()) {
				System.out.println("No user specified, please try again.");
				return;
			}
			fb.deleteUser(UserToDelete); // This removes the user that was asked to be deleted by the user.
			System.out.println("The user has been removed."); // This is a confirmation message.
		}
	}

	private static void addUser() { // This functions as the main way to add User's to the ArrayList.
		System.out.println("Enter the Username of the FacebookUser: ");
		String UName = "", PWord = "", PHint = "";
		if (UserInput.hasNextLine()) {
			UName = UserInput.nextLine(); // This sets the Username of the User to a String that has whatever the
											// User put in within it.
			if (UName.isEmpty()) {
				System.out.println("No Username was entered, please try again."); // This prints if the String is empty.
				return;
			}
		}
		System.out.println("Enter the Password of the FacebookUser"); // This does the same thing as UName and gets a
																		// String with the User's Password.
		if (UserInput.hasNextLine()) {
			PWord = UserInput.nextLine();
			if (PWord.isEmpty()) {
				System.out.println("No password was entered, please try again."); // This prints if the String is empty.
				return;
			}
		}
		System.out.println("Please, enter your User's PasswordHint");
		if (UserInput.hasNextLine()) {
			PHint = UserInput.nextLine(); // And this is the line that gets the User's PasswordHint.
			if (PHint.isEmpty()) {
				System.out.println("No password hint was entered, please try again."); // This prints if the String is
																						// empty.
				return;
			}
		}
		FacebookUser FBUser = new FacebookUser(UName, PWord);
		FBUser.passwordHint = PHint;
		fb.addUser(FBUser);
		System.out.println("User was added!"); // This prints out a message upon completetion.
		return;
	}

	public static void readUsers() { // This reads the fBookUsers.dat file to get the User's list.
		File inputFile = new File(FILENAME); // This sets the File.

		if (!inputFile.exists()) { // If the Input File exists...
			return; // This returns back to the method.
		}
		ObjectInputStream reader; // This calls the ObjectInputStream.
		try {
			reader = new ObjectInputStream(new FileInputStream(FILENAME));
			while (true) {
				FacebookUser user = (FacebookUser) reader.readObject();
				fb.addUser(user);
			}
		} catch (EOFException e) {
			// Do nothing
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void writeUsers() {
		try {
			ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(FILENAME));
			for (FacebookUser user : fb.getUsers()) {
				writer.writeObject(user);
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
