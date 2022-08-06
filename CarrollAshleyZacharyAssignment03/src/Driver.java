import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class Driver {
	private static final PrintStream Print = System.out;
	private static Scanner UserInput = new Scanner(System.in);
	private static final Facebook fb = new Facebook();
	private static final String FILENAME = "fBookUsers.dat";

	public Driver() {
	}

	public static int showMenu() {
		Print.println("Facebook Menu"); // This prints out the menu.
		Print.println("1: List All Current Facebook Users"); // This prints out the menu.
		Print.println("2: Add a Facebook User"); // This prints out the menu.
		Print.println("3: Delete A Facebook User"); // This prints out the menu.
		Print.println("4: Get a User's Password Hint"); // This prints out the menu.
		Print.println("5: Quit");

		return UserInput.nextInt(); // This reads the User's input using the before mentioned scanner.
	}

	public static void main(String[] args) {
		readUsers();
		while (true) {
			try {
				int Menu = showMenu();
				UserInput.nextLine(); // Throw away the return
				switch (Menu) { // Each switch is one of the menu's options. It tells the menu to try and run
								// the methods in the cases when the option is chosen by the user with the above
								// used Scanner asking for their input through a choice.
				case 1:
					listUsers(); // This is the function telling the menu to print the ArrayList.
					break;
				case 2: {
					addUser(); // This tells the menu to try and add the User to the ArrayList.
					break;
				}
				case 3: {
					deleteUser(); // This tells the menu to try and delete the User from the ArrayList.
					break;
				}
				case 4: {
					getPasswordHint(); // This tells the menu to try and retrieve the User's PasswordHint.
					break;
				}
				case 5: {
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

	private static void Quit() { // This method breaks the menu and prints out the message "Goodbye" to the User
									// in the console.
		System.out.println("Goodbye.");
		writeUsers();
	}

	private static void getPasswordHint() {
		System.out.println("Enter UserName of User you'd like to see the PasswordHint of: "); // This attempts to print
																								// out the Password Hint
																								// the User set up for
																								// the
		// FacebookUser who was added to the ArrayList.
		String userName = UserInput.nextLine();
		FacebookUser user = fb.getUserByName(userName);
		if (user == null) {
			System.out.println("User doesn't exist!");
			return;
		}
		System.out.println("Password Hint: " + user.passwordHint);
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

	private static void listUsers() {
		for (FacebookUser user : fb.getUsers())
			System.out.println(user.getName()); // This just prints out the User's ArrayList to tell the User's on it.

	}

	public static void readUsers() {
		File inputFile = new File(FILENAME);

		if (!inputFile.exists()) {
			return;
		}
		ObjectInputStream reader;
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
