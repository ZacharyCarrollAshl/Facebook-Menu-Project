package facebook.menu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeSet;

public class FacebookUser extends UserAccount implements Comparable<FacebookUser>, Cloneable {
	String passwordHint; // Setting up PasswordHint as a string so that way it can be written to the
							// User's account.
	ArrayList<FacebookUser> friends; // This is the ArrayList that will be what we put the User's account information
										// (Username/Password) into during the Driver Program.
	ArrayList<FacebookUser> recommended;
	TreeSet<String> likes; // EXPLANATION : Set to a TreeSet since it is Alphabetical and because Set's are
							// unique values.

	public FacebookUser(String username, String password) {
		super(username, password); // This calls the methods from the Super Class which is User Account.
		String passwordHint = (""); // This sets the PasswordHint for the Account's that will be used in the driver
									// to blank, that way they can be set by the User to anything they'd like along
									// with the method in the abstract UserAccount class.
		friends = new ArrayList<FacebookUser>();
		recommended = new ArrayList<FacebookUser>();
		likes = new TreeSet<String>();
	}

	void setPasswordHint(String hint) {
		this.passwordHint = hint; // This sets up the method which sets up the User's PasswordHint. It is used to
									// set a string to the account which helps the User remember their password.
	}

	public void friend(FacebookUser newFriend) {
		// if (friends.contains(newFriend)) {
		// System.out.println("This friend is already on the friends list.");
		// } else {
		if (friends == null) {
			friends = new ArrayList<FacebookUser>();
		}
		friends.add(newFriend); // This adds the User to the given person's friend's list.
		System.out.println(newFriend + " has been friended."); // This prints out a message saying the newFriend was
																// added.
		UndoStack.add(() -> friends.remove(newFriend)); // Adds an undo action to adding a friend.

	}

	// }

	void defriend(FacebookUser formerFriend) {
		// if (friends.contains(formerFriend)) {
		// System.out.println("This person has not been added to your friends list.");
		// } else {
		friends.remove(formerFriend); // This removes the formerFriend from the User's friend list.
		System.out.println(formerFriend + "has been defriended."); // This prints out a message saying the formerFriend
																	// was unadded.
		UndoStack.add(() -> friends.add(formerFriend)); // Adds an undo action to removing a friend.
	}

	// }
	public ArrayList<FacebookUser> getFriends() {
		if (friends == null) {
			friends = new ArrayList<FacebookUser>();
		}
		return new ArrayList<FacebookUser>(this.friends);
	}

	public ArrayList<FacebookUser> getRecommendedFriends() {
		recommended = new ArrayList<FacebookUser>(); // This creates the Recommended ArrayList.
		return getRecommended(this); // This calls the recursive function to populate the Recommended ArrayList.
	}

	public ArrayList<FacebookUser> getRecommended(FacebookUser user) { // Recursive method requested from Assignment 04
		for (FacebookUser friend : user.getFriends()) { // Looping through friends for recommended friends from the User
														// provided from the Menu.
			if (friend.equals(this))
				continue;
			if (recommended.contains(friend)) // If they are already recommended, skip.
				continue; // This is the skip.
			if (!friends.contains(friend)) // If not already a friend, adds them as a recommended friend.
				recommended.add(friend); // Adds them as a recommended friend.
			getRecommended(friend); // Runs it on said friend.
		}
		Collections.sort(recommended, new FacebookFriendCountComparator()); // Sorts Recommended friends list by the
																			// Friend Count Comparator.
		return recommended; // Returns the recommended.
	}

	@Override
	public int compareTo(FacebookUser o) {
		return this.getName().compareTo(o.getName());
	}

	@Override
	public void getPasswordHelp() {
		System.out.println(passwordHint); // This prints out the User's passwordHint.
	}

	public boolean isNamed(String name) {
		return name.equals(getName());
	}

	public boolean likes(String like) { // Returns true if User is liking the like.
		return likes.contains(like);

	}

	public void addLike(String like) {
		if (!likes.contains(like)) { // If Likes doesn't contain Like...
			likes.add(like); // Adds the User's Like.
		}

	}

	public TreeSet<String> getLikes() { // Returns a new set of Likes.
		return new TreeSet<String>(likes);
	}
}