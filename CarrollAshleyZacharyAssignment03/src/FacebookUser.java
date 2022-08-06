import java.util.ArrayList;
import java.util.Collections;

public class FacebookUser extends UserAccount implements Comparable<FacebookUser>, Cloneable {
	String passwordHint; // Setting up PasswordHint as a string so that way it can be written to the
							// User's account.
	ArrayList<FacebookUser> friends; // This is the ArrayList that will be what we put the User's account information
										// (Username/Password) into during the Driver Program.

	public FacebookUser(String username, String password) {
		super(username, password); // This calls the methods from the Super Class which is User Account.
		String passwordHint = (""); // This sets the PasswordHint for the Account's that will be used in the driver
									// to blank, that way they can be set by the User to anything they'd like along
									// with the method in the abstract UserAccount class.
	}

	void setPasswordHint(String hint) {
		this.passwordHint = hint; // This sets up the method which sets up the User's PasswordHint. It is used to
									// set a string to the account which helps the User remember their password.
	}

	void friend(FacebookUser newFriend) {
		// if (friends.contains(newFriend)) {
		// System.out.println("This friend is already on the friends list.");
		// } else {
		friends.add(newFriend);
		System.out.println(newFriend + "has been friended.");
	}

	// }

	void defriend(FacebookUser formerFriend) {
		// if (friends.contains(formerFriend)) {
		// System.out.println("This person has not been added to your friends list.");
		// } else {
		friends.remove(formerFriend);
		System.out.println(formerFriend + "has been defriended.");
	}

	// }
	public ArrayList<FacebookUser> getFriends() throws CloneNotSupportedException {
		ArrayList<FacebookUser> FriendsCopy = new ArrayList<FacebookUser>();
		FriendsCopy = (ArrayList<FacebookUser>) friends.clone(); // This method clones the friends ArrayList and also
																	// throws the CloneNotSupportedException as the
																	// Clone method wasn't supported with the Array
																	// before we threw the exception.

		return FriendsCopy; // This returns the copy of the friends ArrayList for encapsulation purposes.
	}

	@Override
	public int compareTo(FacebookUser o) {
		Collections.sort(friends); // This sorts the friends ArrayList.
		return 0;
	}

	@Override
	public void getPasswordHelp() {

	}

	public boolean isNamed(String name) {
		return name.equals(getName());
	}
}
